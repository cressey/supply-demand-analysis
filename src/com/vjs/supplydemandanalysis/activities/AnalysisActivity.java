package com.vjs.supplydemandanalysis.activities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.vjs.supplydemandanalysis.R;
import com.vjs.supplydemandanalysis.database.DatabaseDAO;
import com.vjs.supplydemandanalysis.database.EmployeeDAO;
import com.vjs.supplydemandanalysis.database.SkillsSheetDAO;
import com.vjs.supplydemandanalysis.employee.Employee;
import com.vjs.supplydemandanalysis.skills.BarObject;
import com.vjs.supplydemandanalysis.skills.SkillAnalysis;
import com.vjs.supplydemandanalysis.skills.SkillsSheet;
import com.vjs.supplydemandanalysis.view.GraphBar;

public class AnalysisActivity extends SuperActivity implements OnClickListener {

	public enum BarType {
		SUBJECT, SKILLGROUP, SKILL;
	}

	private List<SkillAnalysis> skillsAnalysis;
	// The database interaction object
	private DatabaseDAO databaseDao = new DatabaseDAO(this);
	// The skills sheet interaction object
	private SkillsSheetDAO skillsSheetDao = new SkillsSheetDAO(this);
	// The employee interaction object
	private EmployeeDAO employeeDao = new EmployeeDAO(this);
	// The graph table from the layout
	private TableLayout graphTable;
	// The table rows for the graphics and for the labels
	private TableRow barGraphics, labels, counts;
	// The scrolling view for the graph
	private HorizontalScrollView graphContainer;
	// The text view for the header
	private TextView headerText;
	// The back button
	private ImageButton backBtn;
	// The type of selected 'thing' - skills sheet or employee
	private ArrayList<String> keys;
	// The id of the selected sheet or employee
	private ArrayList<Integer> values;
	// The list of selected skills sheets ids
	private ArrayList<Integer> skillSheetIds = new ArrayList<Integer>();
	// The list of selected employee ids
	private ArrayList<Integer> employeeIds = new ArrayList<Integer>();
	private String skillsSheetNames = "";
	private Integer selectedSubjectId;
	private int BAR_WIDTH = 80;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	/**
	 * When the activity is opened or resumed, flow goes through this method. It
	 * sets up the selected skills sheets and employees then populates the graph
	 */
	@Override
	protected void onResume() {
		super.onResume();
		setContentView(R.layout.graphlayout);
		setUpCoreButtonControls(this);
		backBtn = (ImageButton) findViewById(R.id.backButton);
		// Get the graph table layout
		graphTable = (TableLayout) findViewById(R.id.graphViewTable);
		// Get the counts row
		counts = (TableRow) findViewById(R.id.countsRow);
		// Get the label row
		labels = (TableRow) findViewById(R.id.labelsRow);
		// Get the bar table row
		barGraphics = (TableRow) findViewById(R.id.barsRow);
		// List<BarObject> graphBars = new ArrayList<BarObject>();
		// Get the text view for the header
		headerText = (TextView) findViewById(R.id.headerText);
		headerText.setOnClickListener(this);
		// Get the scrolling graph container
		graphContainer = (HorizontalScrollView) findViewById(R.id.graphContainer);
		// Get the list of selected skills sheets and the list
		// of selected employees.
		keys = getIntent().getStringArrayListExtra("keys");
		values = getIntent().getIntegerArrayListExtra("values");
		ArrayList<SkillsSheet> selectedSkillsSheets = new ArrayList<SkillsSheet>();
		ArrayList<Employee> selectedEmployees = new ArrayList<Employee>();
		for (int i = 0; i < keys.size(); i++) {
			if (keys.get(i).equals("skillsSheet")) {
				selectedSkillsSheets.add(skillsSheetDao.getSkillsSheet(values
						.get(i)));
				skillsSheetNames = skillsSheetNames
						+ System.getProperty("line.separator")
						+ skillsSheetDao.getSkillsSheet(values.get(i))
								.getName();
			} else if (keys.get(i).equals("employee")) {
				selectedEmployees.add(employeeDao.getEmployee(values.get(i)));
			}
		}
		// Get all the ids from the selected skills sheets and employees
		for (SkillsSheet sheet : selectedSkillsSheets) {
			skillSheetIds.add(sheet.getId());
		}
		for (Employee employee : selectedEmployees) {
			employeeIds.add(employee.getId());
		}
		// Get all the skills and their analysis
		skillsAnalysis = databaseDao.getSkillsAnalysis(skillSheetIds,
				employeeIds);
		// Populate the graph from the selected sheets and employees
		populateGraph(null, BarType.SUBJECT);
	}

	/**
	 * This method populates the graph *
	 * 
	 * @param thingId
	 *            the previously selected 'thing' be it a subject or skills
	 *            group
	 * @param type
	 *            the
	 */
	private void populateGraph(Integer thingId, BarType type) {
		List<BarObject> graphBars = new ArrayList<BarObject>();
		boolean meetsDemand = false;
		// Clear the views on the page ready for the next set to be displayed
		graphContainer.removeAllViews();
		labels.removeAllViews();
		counts.removeAllViews();
		barGraphics.removeAllViews();
		// Get the bars for the subject graph
		if (type == BarType.SUBJECT) {
			// meetsDemand = databaseDao.getDisciplineOverview(skillSheetIds,
			// employeeIds);
			graphBars = new ArrayList<BarObject>(getSubjectBars());
			// for (BarObject bar : graphBars) {
			// bar.setMeetsDemand(databaseDao.getSubjectOverview(bar.getId(),
			// skillSheetIds, employeeIds));
			// }
			backBtn.setOnClickListener(this);
		}
		// Get the bars for the skill group graph
		else if (type == BarType.SKILLGROUP) {
			// meetsDemand = databaseDao.getSubjectOverview(thingId,
			// skillSheetIds, employeeIds);
			// graphBars = databaseDao.getSkillGroupDemandForSkillsSheet(
			// skillSheetIds, employeeIds, thingId);
			// for (BarObject bar : graphBars) {
			// bar.setMeetsDemand(databaseDao.getSkillGroupOverview(
			// bar.getId(), skillSheetIds, employeeIds));
			// }
			graphBars = new ArrayList<BarObject>(getSkillGroupBars(thingId));
			backBtn.setOnClickListener(new OnClickListener() {
				public void onClick(View arg0) {
					populateGraph(null, BarType.SUBJECT);
				}
			});
		}
		// Get the bars for the skills graph
		else if (type == BarType.SKILL) {
			// graphBars = databaseDao.getSkillSupplyAndDemandForSkillsSheet(
			// skillSheetIds, employeeIds, thingId);
			graphBars = new ArrayList<BarObject>(getSkillBars(thingId));
			backBtn.setOnClickListener(new OnClickListener() {
				public void onClick(View arg0) {
					populateGraph(selectedSubjectId, BarType.SKILLGROUP);
				}
			});
		}
		if (type == BarType.SKILL) {
			drawSkillsBars(graphBars);
		} else {
			// drawBars(graphBars, meetsDemand);
			drawBars(graphBars);
		}
	}

	private Collection<BarObject> getSubjectBars() {
		Map<Integer, BarObject> bars = new HashMap<Integer, BarObject>();
		for (SkillAnalysis sa : skillsAnalysis) {
			BarObject bar = bars.get(sa.getSubjectId());
			if (bar == null) {
				bar = new BarObject();
				bar.setType(BarType.SUBJECT);
				bar.setName(sa.getSubject());
				bar.setId(sa.getSubjectId());
				bar.setDemand(sa.getDemand());
				bar.setMeetsDemand(sa.getSupply() >= sa.getDemand());
			} else {
				bar.setDemand(bar.getDemand() + sa.getDemand());
				bar.setMeetsDemand(bar.isMeetsDemand()
						&& (sa.getSupply() >= sa.getDemand()));
			}
			bars.put(bar.getId(), bar);
		}
		return bars.values();
	}

	private Collection<BarObject> getSkillGroupBars(int subjectId) {
		Map<Integer, BarObject> bars = new HashMap<Integer, BarObject>();
		for (SkillAnalysis sa : skillsAnalysis) {
			if (sa.getSubjectId() == subjectId) {
				BarObject bar = bars.get(sa.getSkillGroupId());
				if (bar == null) {
					bar = new BarObject();
					bar.setType(BarType.SKILLGROUP);
					bar.setName(sa.getSkillGroup());
					bar.setId(sa.getSkillGroupId());
					bar.setDemand(sa.getDemand());
					bar.setMeetsDemand(sa.getSupply() >= sa.getDemand());
				} else {
					bar.setDemand(bar.getDemand() + sa.getDemand());
					bar.setMeetsDemand(bar.isMeetsDemand()
							&& (sa.getSupply() >= sa.getDemand()));
				}
				bars.put(bar.getId(), bar);
			}
		}
		return bars.values();
	}

	private Collection<BarObject> getSkillBars(int skillGroupId) {
		Map<Integer, BarObject> bars = new HashMap<Integer, BarObject>();
		for (SkillAnalysis sa : skillsAnalysis) {
			if (sa.getSkillGroupId() == skillGroupId) {
				BarObject bar = bars.get(sa.getSkillId());
				if (bar == null) {
					bar = new BarObject();
					bar.setType(BarType.SKILL);
					bar.setName(sa.getSkillName());
					bar.setId(sa.getSkillId());
					bar.setDemand(sa.getDemand());
					bar.setSupply(sa.getSupply());
					bar.setMeetsDemand(sa.getSupply() >= sa.getDemand());
				} else {
					bar.setDemand(bar.getDemand() + sa.getDemand());
					bar.setMeetsDemand(bar.isMeetsDemand()
							&& (sa.getSupply() >= sa.getDemand()));
				}
				bars.put(bar.getId(), bar);
			}
		}
		return bars.values();
	}

	/**
	 * When displaying skills, both bars need to be shown and meets demand is
	 * calculated differently so use this method
	 * 
	 * @param graphBars
	 */
	private void drawSkillsBars(List<BarObject> graphBars) {
		for (BarObject bar : graphBars) {
			bar.getId();
			// The number of demands for a skill
			TextView demandCount = new TextView(this);
			demandCount.setText(" D:" + Integer.toString(bar.getDemand()));
			counts.addView(demandCount);
			demandCount.setWidth(55);
			demandCount.setGravity(Gravity.CENTER);
			// The number of people capable of doing a skill
			TextView supplyCount = new TextView(this);
			supplyCount.setText("S:" + Integer.toString(bar.getSupply()));
			supplyCount.setWidth(55);
			supplyCount.setGravity(Gravity.CENTER);
			counts.addView(supplyCount);
			// The skill name
			TextView label = new TextView(this);
			label.setText(bar.getName());
			label.setGravity(Gravity.CENTER);
			label.setPadding(0, 10, 5, 5);
			TableRow.LayoutParams params = new TableRow.LayoutParams();
			params.span = 2;
			label.setLayoutParams(params);
			labels.addView(label);
			// If a skill is clicked then display all employees capable of doing
			// that skill
			OnClickListener onClickListener = new OnClickListener() {
				public void onClick(View arg0) {
					Intent i = new Intent(AnalysisActivity.this,
							EmployeeSupplyActivity.class);
					startActivity(i);
				}
			};
			// If the supply matches the demand colour the bars green, otherwise
			// red
			boolean meetDemand;
			if (bar.getDemand() > bar.getSupply()) {
				meetDemand = false;
			} else {
				meetDemand = true;
			}
			// Create
			GraphBar demandBar = new GraphBar(this, bar.getDemand() * 100,
					BAR_WIDTH, meetDemand);
			demandBar.setOnClickListener(onClickListener);
			GraphBar supplyBar = new GraphBar(this, bar.getSupply() * 100,
					BAR_WIDTH, meetDemand);
			supplyBar.setOnClickListener(onClickListener);
			barGraphics.setPadding(5, 10, 5, 5);
			barGraphics.addView(demandBar);
			barGraphics.addView(supplyBar);
		}
		graphContainer.addView(graphTable);
	}

	/**
	 * For drawing the graph bars for all graphs other than the skill
	 * 
	 * @param graphBars
	 */
	private void drawBars(List<BarObject> graphBars) {
		// For each bar get the id and the name, draw it, and add it to the view
		for (BarObject bar : graphBars) {
			final int id = bar.getId();
			final BarType barType = bar.getType();
			TextView label = new TextView(this);
			label.setText(bar.getName());
			label.setGravity(Gravity.CENTER);
			label.setPadding(0, 10, 5, 5);
			label.setWidth(BAR_WIDTH + 10);
			labels.addView(label);
			OnClickListener onClickListener = new OnClickListener() {
				public void onClick(View arg0) {
					BarType barTypeNextLevel = null;
					switch (barType) {
					case SUBJECT:
						barTypeNextLevel = BarType.SKILLGROUP;
						selectedSubjectId = id;
						populateGraph(id, barTypeNextLevel);
						break;
					case SKILLGROUP:
						barTypeNextLevel = BarType.SKILL;
						populateGraph(id, barTypeNextLevel);
						break;
					}
				}
			};
			GraphBar demandBar = new GraphBar(this, 500, BAR_WIDTH,
					bar.isMeetsDemand());
			demandBar.setOnClickListener(onClickListener);
			barGraphics.addView(demandBar);
		}
		graphContainer.addView(graphTable);
	}

	/**
	 * Set up the click listeners
	 */
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.backButton:
			AnalysisActivity.this.finish();
			break;
		case R.id.homeButton:
			Intent i = new Intent(this, HomeActivity.class);
			startActivity(i);
			AnalysisActivity.this.finish();
			break;
		case R.id.helpButton:
			String message = "Add a decent help message here please!";
			new AlertDialog.Builder(AnalysisActivity.this)
					.setTitle("Help")
					.setMessage(message)
					.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
								}
							}).show();
			break;
		case R.id.headerText:
			String selectedSheets = skillsSheetNames == "" ? "All"
					: skillsSheetNames;
			new AlertDialog.Builder(AnalysisActivity.this)
					.setTitle("Selected Skills Sheets")
					.setMessage(selectedSheets)
					.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
								}
							}).show();
			break;
		}
	}
}
