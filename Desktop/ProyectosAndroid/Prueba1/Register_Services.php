<?php
    $conn = mysqli_connect("localhost", "id3110915_jrd123", "12345", "id3110915_data");
    
    $service = $_POST["service"];
    $autor = $_POST["autor"];
    $desc = $_POST["desc"];
    $fecha = date("Y/m/d h:i:s");

    
	$stmt = mysqli_prepare($conn, "SELECT service_id FROM services WHERE service like ?");

    /* ligar parámetros para marcadores */
    mysqli_stmt_bind_param($stmt, "s", $service);

    /* ejecutar la consulta */
    mysqli_stmt_execute($stmt);
    
    mysqli_stmt_store_result($stmt);

      
	$rows = mysqli_stmt_num_rows($stmt);
	
    $response = array();
	if($rows>0){
	    $response["Exito"] = false;
	    $response["Mensaje"] = "Ya existe un servicio con el nombre ingresado";
	}
  	else{
        $stat = mysqli_prepare($conn, "INSERT INTO services (service, autor, Descripcion, fecha) VALUES (?, ?, ?, ?)");
        mysqli_stmt_bind_param($stat, "ssss", $service, $autor, $desc, $fecha);
        mysqli_stmt_execute($stat);
        $response["Exito"] = true;  
        $response["Mensaje"] = "Se ha guardado con éxito";
    }
    
    echo json_encode($response);
?>