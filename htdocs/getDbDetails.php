<?php

include("sqlConnect.php");
$newline = "\n";

$username = mysql_real_escape_string($_GET['username']);
$password = mysql_real_escape_string($_GET['password']);



$query = "SELECT discipline
          FROM user
          WHERE username = '$username' 
            AND password = '$password' ";
$result = mysql_query($query);

if(mysql_num_rows($result) == 1) {
    // correct login
    $userData = mysql_fetch_array($result, MYSQL_ASSOC);
    $discipline = $userData['discipline'];
    
    $subjects = array();
    $skillGroups = array();
	$materialGroups = array();
	$contextGroups = array();
    
    
    // now use the discipline to get the subject data
    $sql = "SELECT * FROM subject WHERE DisciplineID = '$discipline'";
    $result = mysql_query($sql);
    $values = array();
    // get first row
    $row=mysql_fetch_array($result);
    $firstInsert = "INSERT OR IGNORE INTO `subjectGroupTable` SELECT ".$row['ID']." AS `subjectId`, '".$row['Name']."' AS `subjectName`, ".$row['DisciplineID']." AS `disciplineGroup` ";
    $subjects[] = $row['ID'];
    while ($row=mysql_fetch_array($result)) {
        $values[] = " UNION SELECT ".$row['ID'].", '".$row['Name']."', ".$row['DisciplineID'];
        $subjects[] = $row['ID'];
    }
    echo $firstInsert.implode(" ", $values).$newline;
    
    
    // now use the subject IDs to get the skill groups
    $sql = "SELECT * FROM skillgroup WHERE SubjectID IN ( ".implode(", ", $subjects)." )";
    $result = mysql_query($sql);
    $values = array();
    // get first row
    $row=mysql_fetch_array($result);
    $firstInsert = "INSERT OR IGNORE INTO `SkillGroupTable` SELECT ".$row['ID']." AS `SkillGroupId`, '".$row['Name']."' AS `SkillGroupName`, ".$row['SubjectID']." AS `subjectId` ";
    $skillGroups[] = $row['ID'];
    while ($row=mysql_fetch_array($result)) {
        $values[] = " UNION SELECT ".$row['ID'].", '".$row['Name']."', ".$row['SubjectID'];
        $skillGroups[] = $row['ID'];
    }
    echo $firstInsert.implode(" ", $values).$newline;
    
    
    // now use the skill group IDs to get the skills
    $sql = "SELECT * FROM skill WHERE skillGroupID IN ( ".implode(", ", $skillGroups)." )";
    $result = mysql_query($sql);
    $values = array();
    // get first row
    $row=mysql_fetch_array($result);
    $firstInsert = "INSERT OR IGNORE INTO `SkillTable` SELECT ".$row['ID']." AS `SkillId`, '".$row['Name']."' AS `SkillName`, ".$row['skillGroupID']." AS `SkillGroupId` ";
    while ($row=mysql_fetch_array($result)) {
        $values[] = " UNION SELECT ".$row['ID'].", '".$row['Name']."', ".$row['skillGroupID'];
    }
    echo $firstInsert.implode(" ", $values).$newline;
	
	 // now use the discipline to get the material group data
    $sql = "SELECT * FROM materialgroup WHERE DisciplineID = '$discipline'";
    $result = mysql_query($sql);
    $values = array();
    // get first row
    $row=mysql_fetch_array($result);
    $firstInsert = "INSERT OR IGNORE INTO `MaterialGroupTable` SELECT ".$row['ID']." AS `MaterialGroupId`, '".$row['Name']."' AS `MaterialGroupName`, ".$row['DisciplineID']." AS `disciplineGroup` ";
    $materialGroups[] = $row['ID'];
    while ($row=mysql_fetch_array($result)) {
        $values[] = " UNION SELECT ".$row['ID'].", '".$row['Name']."', ".$row['DisciplineID'];
        $materialGroups[] = $row['ID'];
    }
    echo $firstInsert.implode(" ", $values).$newline;
    
    // now use the material group IDs to get the materials
    $sql = "SELECT * FROM material WHERE MaterialGroupID IN ( ".implode(", ", $materialGroups)." )";
    $result = mysql_query($sql);
    $values = array();
    // get first row
    $row=mysql_fetch_array($result);
    $firstInsert = "INSERT OR IGNORE INTO `MaterialTable` SELECT ".$row['ID']." AS `MaterialId`, '".$row['Name']."' AS `MaterialName`, ".$row['MaterialGroupID']." AS `MaterialGroupId` ";
    while ($row=mysql_fetch_array($result)) {
        $values[] = " UNION SELECT ".$row['ID'].", '".$row['Name']."', ".$row['MaterialGroupID'];
    }
    echo $firstInsert.implode(" ", $values).$newline;
    
    // now use the discipline to get the context group data
    $sql = "SELECT * FROM contextgroup WHERE DisciplineID = '$discipline'";
    $result = mysql_query($sql);
    $values = array();
    // get first row
    $row=mysql_fetch_array($result);
    $firstInsert = "INSERT OR IGNORE INTO `ContextGroupTable` SELECT ".$row['ID']." AS `ContextGroupId`, '".$row['Name']."' AS `ContextGroupName`, ".$row['DisciplineID']." AS `disciplineGroup` ";
    $contextGroups[] = $row['ID'];
    while ($row=mysql_fetch_array($result)) {
        $values[] = " UNION SELECT ".$row['ID'].", '".$row['Name']."', ".$row['DisciplineID'];
        $contextGroups[] = $row['ID'];
    }
    echo $firstInsert.implode(" ", $values).$newline;
    
    // now use the context group IDs to get the contexts
    $sql = "SELECT * FROM context WHERE ContextGroupID IN ( ".implode(", ", $contextGroups)." )";
    $result = mysql_query($sql);
    $values = array();
    // get first row
    $row=mysql_fetch_array($result);
    $firstInsert = "INSERT OR IGNORE INTO `ContextTable` SELECT ".$row['ID']." AS `ContextId`, '".$row['Name']."' AS `ContextName`, ".$row['ContextGroupID']." AS `ContextGroupId` ";
    while ($row=mysql_fetch_array($result)) {
        $values[] = " UNION SELECT ".$row['ID'].", '".$row['Name']."', ".$row['ContextGroupID'];
    }
    echo $firstInsert.implode(" ", $values).$newline;
    
    
} else {
    // not correct login
    echo "error".$newline;
    echo "Please try to login again";
    die();
}
