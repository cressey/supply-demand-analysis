<html>
    <head>
		<title>Task Entry</title>
    </head>
	<?php
		include("sqlConnect.php");
		$toDisplay="";
		$optionsDiscipline="";
		$optionsSubject="";
		$optionsTaskGroup="";
		$optionsMaterialGroup="";
		$optionsContextGroup="";
		
		//This function populates a dropdown menu of all the names from the table that is set as the parameter
		function populateDropdown($table, $options) {
			$result=mysql_query("SELECT ID, Name FROM $table") or die(mysql_error());
			$numRows=mysql_num_rows($result);
			$counter=0;
			while ($row=mysql_fetch_array($result)) {
				$counter++;
				$name=$row["Name"];
				$id=$row["ID"];
				if ($counter == $numRows) {
					$options.="<OPTION selected='yes' VALUE='$id'>$name</option>";
				} else {
					$options.="<OPTION VALUE='$id'>$name</option>";
				}
			}
			return $options;
		}
		//This functions enters the selected data into the relevant table
		function enterData($parent, $child, $parentTable, $entryTable, $column, $header) {
			if( isset($_POST[$child]) && $_POST[$child] != "" && 
					isset($_POST[$parent]) && $_POST[$parent] != '0' && $_POST[$parent] != "") {
				$item=mysql_real_escape_string($_POST[$child]);	
				$itemParent=mysql_real_escape_string($_POST[$parent]);
				// Only put it into the table if the foreign key exists
				$result=mysql_query("SELECT ID FROM $parentTable WHERE ID = '$itemParent'") or die(mysql_error());
				if (mysql_num_rows($result) == 1) {
					$row=mysql_fetch_array($result);
					$id=$row['ID'];
					mysql_query("INSERT INTO $entryTable (Name, $column) VALUES ('$item', '$id')") or die(mysql_error());
					$result=mysql_query("SELECT * FROM $entryTable WHERE $column = '$itemParent'") or die(mysql_error());
					$row=mysql_fetch_array($result);
					$toDisplay.="<table><tr><th>ID</th><th>Name</th><th>$header</th></tr>";
					while ($row) {
						$toDisplay.="<tr><td>".$row["ID"]."</td><td>".$row["Name"]."</td><td>".$row[$column]."</td></tr>";
						$row=mysql_fetch_array($result);
					}
					$toDisplay.="</table>";
				} else {
					echo "That material group is currently not stored in the database";
				}	
			}
		}
	
		//If save discipline is pressed, put the entry into the discipline table
		if( isset($_POST['discipline'])) {
			$discipline = mysql_real_escape_string($_POST['discipline']);				
			mysql_query("INSERT INTO Discipline (Name) VALUES ('$discipline')") or die(mysql_error());
			$result=mysql_query("SELECT * FROM Discipline");
			$row=mysql_fetch_array($result);
			$toDisplay.="<table><tr><th>ID</th><th>Name</th></tr>";
			while ($row) {
				$toDisplay.="<tr><td>".$row["ID"]."</td><td>".$row["Name"]."</td></tr>";
				$row=mysql_fetch_array($result);
			}
			$toDisplay.="</table>";
		}
		//If save subject is pressed, put the entry into the subject table
		enterData("disciplineDropdown", "subject", "discipline", "subject", "DisciplineID", "Discipline ID");
		//If save task group is pressed, put the entry into the task group table
		enterData("subjectDropdown", "taskGroup", "subject", "taskgroup", "SubjectID", "Subject ID");
		//If save task  is pressed, put the entry into the task  table
		enterData("taskGroupDropdown", "task", "taskGroup", "task", "TaskGroupID", "Task Group ID");
		//If save material group is pressed, put the entry into the material group table
		enterData("disciplineDropdown", "materialGroup", "discipline", "materialGroup", "DisciplineID", "Discipline ID");
		//If save material is pressed, put the entry into the material table
		enterData("materialGroupDropdown", "material", "materialgroup", "material", "MaterialGroupID", "Material Group ID");
		//If save context group is pressed, put the entry into the context group table
		enterData("disciplineDropdown", "contextGroup", "discipline", "contextGroup", "DisciplineID", "Discipline ID");
		//If save context is pressed, put the entry into the context table
		enterData("contextGroupDropdown", "context", "contextgroup", "context", "ContextGroupID", "Context Group ID");
		
		//Populate a drop down box of Disciplines currently stored in the database
		$optionsDiscipline=populateDropdown("discipline", $optionsDiscipline);
		//Populate a drop down box of Subjects currently stored in the database
		$optionsSubject=populateDropdown("subject", $optionsSubject);
		//Populate a drop down box of Task Groups currently stored in the database
		$optionsTaskGroup=populateDropdown("taskgroup", $optionsTaskGroup);
		//Populate a drop down box of Material Groups currently stored in the database
		$optionsMaterialGroup=populateDropdown("materialgroup", $optionsMaterialGroup);
		//Populate a drop down box of Context Groups currently stored in the database
		$optionsContextGroup=populateDropdown("contextgroup", $optionsContextGroup);
		
	?>
	
	
	<body>        
		<form id="createDiscipline" action="index.php" method="POST">
			<p>
				<label for="discipline">Discipline</label>
				<br>
				<label for="name">Discipline Name</label>				
				<input type="text" id="discipline" name="discipline" />
				<br>
				<input type="submit" value="Save Discipline" />
			</p>
		</form>
		<form id="createSubject" action="index.php" method="POST">
			<p>
				<label for="subject">Subject</label>
				<br>
				<label for="name">Subject Name</label>
				<input type="text" id="subject" name="subject" />
				<br>
				<label for="disciplineID">Discipline</label>
				<select name="disciplineDropdown">
					<option value=0>Select a discipline<?php echo $optionsDiscipline;?>
				</select>
				<br>
				<input type="submit" value="Save Subject" />
			</p>
		</form>
		<form id="createTaskGroup" action="index.php" method="POST">
			<p>
				<br>
				<label for="tasks"><b><font size="5">Tasks</font></b></label>
				<br>
				<label for="taskGroup">Task Group</label>
				<br>
				<label for="name">Task Group Name</label>
				<input type="text" id="taskGroup" name="taskGroup" />
				<br>
				<label for="subjectID">Subject</label>
				<select name="subjectDropdown">
					<option value=0>Select a subject<?php echo $optionsSubject;?>
				</select>
				<br>
				<input type="submit" value="Save Task Group" />
			</p>
		</form>
		<form id="createTask" action="index.php" method="POST">
			<p>
				<label for="task">Task</label>
				<br>
				<label for="name">Task Name</label>
				<input type="text" id="task" name="task" />
				<br>
				<label for="taskGroupID">Task Group</label>
				<select name="taskGroupDropdown">
					<option value=0>Select a subject<?php echo $optionsTaskGroup;?>
				</select>
				<br>
				<input type="submit" value="Save Task" />
			</p>
		</form>
		<form id="createMaterialGroup" action="index.php" method="POST">
			<p>
				<br>
				<label for="materials"><b><font size="5">Materials</font></b></label>
				<br>
				<label for="materialGroup">Material Group</label>
				<br>
				<label for="name">Material Group Name</label>				
				<input type="text" id="materialGroup" name="materialGroup" />
				<br>
				<label for="disciplineID">Discipline</label>
				<select name="disciplineDropdown">
					<option value=0>Select a discipline<?php echo $optionsDiscipline;?>
				</select>
				<br>
				<input type="submit" value="Save Material Group" />
			</p>
		</form>
		<form id="createMaterial" action="index.php" method="POST">
			<p>
				<label for="material">Material</label>
				<br>
				<label for="name">Material Name</label>
				<input type="text" id="material" name="material" />
				<br>
				<label for="materialGroupID">Material Group</label>
				<select name="materialGroupDropdown">
					<option value=0>Select a material group<?php echo $optionsMaterialGroup;?>
				</select>
				<br>
				<input type="submit" value="Save Material" />
			</p>
		</form>
		<form id="createContextGroup" action="index.php" method="POST">
			<p>
				<br>
				<label for="contexts"><b><font size="5">Contexts</font></b></label>
				<br>
				<label for="contextGroup">Context Group</label>
				<br>
				<label for="name">Context Group Name</label>				
				<input type="text" id="contextGroup" name="contextGroup" />
				<br>
				<label for="disciplineID">Discipline</label>
				<select name="disciplineDropdown">
					<option value=0>Select a discipline<?php echo $optionsDiscipline;?>
				</select>
				<br>
				<input type="submit" value="Save Context Group" />
			</p>
		</form>
		<form id="createContext" action="index.php" method="POST">
			<p>
				<label for="context">Context</label>
				<br>
				<label for="name">Context Name</label>
				<input type="text" id="context" name="context" />
				<br>
				<label for="contextGroupID">Context Group</label>
				<select name="contextGroupDropdown">
					<option value=0>Select a v group<?php echo $optionsContextGroup;?>
				</select>
				<br>
				<input type="submit" value="Save Context" />
			</p>
		</form>
		<p>
			<a href="materialEntry.php" />Material Data Entry</a>
			<br>
			<a href="contextEntry.php" />Context Data Entry</a>
			<br>
			<a href="outputter.php" />Data Extraction</a>
			<br>
			<a href="updatePersonRecord.php" />Person Data Entry</a>
			<br>
			<a href="updateClientList.php" />Create Client List</a>
		</p>
		<?php
			echo $toDisplay;
		?>
    </body>
</html>    

























