<?php
  $mysql_host = "mysql15.000webhost.com";
  $mysql_database = "a8627302_sda";
  $mysql_user = "a8627302_sda";
  $mysql_password = "vjisgreat1";
 
  //mysql_connect("192.168.0.3", "sda", "supplydemand") or die(mysql_error());
  mysql_connect($mysql_host, $mysql_user, $mysql_password) or die(mysql_error());
  mysql_select_db($mysql_database) or die(mysql_error());
?>