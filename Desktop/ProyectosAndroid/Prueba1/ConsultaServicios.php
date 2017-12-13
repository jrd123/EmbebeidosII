<?php
    
$con = new mysqli("localhost", "id3110915_jrd123", "12345", "id3110915_data");   
$q="SELECT *      
FROM services 
order by fecha desc";
    
if($query = $con->prepare($q)){
      
	$query->execute();
      
	$query->store_result();
      
	$rows = $query->num_rows;
      
	$result=array();
        
	$query->bind_result($ans['service_id'],$ans['service'], 
			    $ans['autor'], $ans['Descripcion'], 
			    $ans['fecha']);
        
	for ($i=0;$i<$rows;$i++){
            
		$query->data_seek($i);
            
		$query->fetch();
            
		foreach ($ans as $key=>$value){
                
			$result[$i][$key]=$value;
            
		}
        
	}
	$query->close();
        
	$con->close();
        
	echo json_encode($result,500);
      
    
}  

