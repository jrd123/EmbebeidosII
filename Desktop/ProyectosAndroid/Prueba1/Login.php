<?php
    $con = mysqli_connect("localhost", "id3110915_jrd123", "12345", "id3110915_data");
    
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
	if($rows==0){
        $response["success"] = false;
        $response["Mensaje"] ="El usuario no se encuentra registrado";
	}
	else 
	{
        $statement = mysqli_prepare($con, "SELECT * FROM user WHERE username = ? AND password = ?");
        mysqli_stmt_bind_param($statement, "ss", $username, $password);
        mysqli_stmt_execute($statement);
        
        mysqli_stmt_store_result($statement);
        $rows = mysqli_stmt_num_rows($statement);
        if($rows>0){
            mysqli_stmt_bind_result($statement, $userID, $name, $username, $age, $password);
            
            while(mysqli_stmt_fetch($statement)){
                $response["success"] = true;  
                $response["name"] = $name;
        		$response["age"] = $age;
                $response["username"] = $username;
                $response["password"] = $password;
                $response["Mensaje"] = "Bienvenido " . $username;
            }
        }
        else{
            $response["success"] = false;
            $response["Mensaje"] ="La contraseña no corresponde al usuario";
        }
	}
    echo json_encode($response);
?>