<?php
    $con = mysqli_connect("localhost", "id3110915_jrd123", "12345", "id3110915_data");
    
    $name = $_POST["name"];
	$age = $_POST["age"];
    $username = $_POST["username"];
    $password = $_POST["password"];
	
	$stmt = mysqli_prepare($con, "SELECT user_id FROM user WHERE username like ?");

    /* ligar parámetros para marcadores */
    mysqli_stmt_bind_param($stmt, "s", $username);

    /* ejecutar la consulta */
    mysqli_stmt_execute($stmt);
    
    mysqli_stmt_store_result($stmt);

      
	$rows = mysqli_stmt_num_rows($stmt);
	
    $response = array();
	if($rows>0){
        $response["success"] = false;
        $response["Mensaje"] ="El usuario ingresado no esta disponible";
	}
	else 
	{
	    $statement = mysqli_prepare($con, "INSERT INTO user (name, username, age, password) VALUES (?, ?, ?, ?)");
        mysqli_stmt_bind_param($statement, "ssis", $name, $username, $age, $password);
        mysqli_stmt_execute($statement);
        $response["success"] = true;  
        $response["Mensaje"] = "Se ha registrado con éxito " . $username;
	}   

    
    echo json_encode($response);
?>