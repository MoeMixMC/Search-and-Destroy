<?php
require("config/db.config.php");

//Connect to MySQL database
if (!@mysql_connect($host,$username,$password)) {
    die("Unable to connect to MySQL database!");
}

//See if database is selectable
if (!@mysql_select_db($database)) {
    die("Unable to select database!");
}
$query="SELECT * FROM $db_tbl";
$result=mysql_query($query);

$num=mysql_numrows($result);


?>

<html>
<head>
</head>

<body>
<table border="0" cellspacing="2" cellpadding="2">
<tr>
<td><font face="Arial, Helvetica, sans-serif"><b>Kills</b></font></td>
<td></td>
<td><font face="Arial, Helvetica, sans-serif"><b>Death</b></font></td>
<td></td>
<td><font face="Arial, Helvetica, sans-serif"><b>KDR</b></font></td>
<td></td>
<td><font face="Arial, Helvetica, sans-serif"><b>Wins</b></font></td>
<td></td>
<td><font face="Arial, Helvetica, sans-serif"><b>Loses</b></font></td>


</tr>

<?php
$i=0;
while ($i < $num) {
$f0=mysql_result($result,$i,"kills");
$f1=mysql_result($result,$i,"deaths");
$f2=mysql_result($result,$i,"kdr");
$f3=mysql_result($result,$i,"wins");
$f4=mysql_result($result,$i,"loses");
?>

<tr>
<td><font face="Arial, Helvetica, sans-serif"><?php echo $f0; ?></font></td>
<td></td>
<td><font face="Arial, Helvetica, sans-serif"><?php echo $f1; ?></font></td>
<td></td>
<td><font face="Arial, Helvetica, sans-serif"><?php echo $f2; ?></font></td>
<td></td>
<td><font face="Arial, Helvetica, sans-serif"><?php echo $f3; ?></font></td>
<td></td>
<td><font face="Arial, Helvetica, sans-serif"><?php echo $f4; ?></font></td>
</tr>


<?php
$i++;
}
?>

</body>
</html>
