<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>

  <meta charset="utf-8" name="viewport" content="width=device-width, initial-scale=1">
  <title>Initialise Screen</title>

  <script type="text/javascript" src="<c:url value="/webjars/jquery/3.6.0/jquery.min.js"/>"></script>
  <script type="text/javascript" src="<c:url value="/webjars/bootstrap/5.1.3/js/bootstrap.min.js"/>"></script>
  <script type="text/javascript" src="<c:url value="/resources/javascript/index.js"/>"></script>
  
  <link rel="stylesheet" href="<c:url value="/webjars/bootstrap/5.1.3/css/bootstrap.min.css"/>"/>  
  <link href="<c:url value="/webjars/font-awesome/6.0.0/css/all.css"/>" rel="stylesheet">
		
</head>
<body onload="initialiseForm('initialise')">
<form:form name="initialise_form" autocomplete="off" action="output" method="POST">
<div class="content py-5" style="background-color: #EAE8FF; color: #2E008B">
  <div class="container">
	<div class="row">
	 <div class="col-md-8 offset-md-2">
       <span class="anchor"></span>
         <div class="card card-outline-secondary">
           <div class="card-header">
             <h3 class="mb-0">Initialise</h3>
           </div>
          <div class="card-body">
			  <div class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
			    <label for="select_cricket_matches" class="col-sm-4 col-form-label text-left">Select Match </label>
			    <div class="col-sm-6 col-md-6">
			      <select id="selectedMatch" name="selectedMatch" 
			      		class="browser-default custom-select custom-select-sm">
						<c:forEach items = "${match_files}" var = "match">
							<option value="${match.name}">${match.name}</option>
						</c:forEach>
			      </select>
			    </div>
			  </div>
			  <div class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
			    <label for="select_broadcaster" class="col-sm-4 col-form-label text-left">Select Broadcaster </label>
			    <div class="col-sm-6 col-md-6">
			      <select id="select_broadcaster" name="select_broadcaster" class="browser-default custom-select custom-select-sm"
			      		onchange="processUserSelection(this)">
			          <option value="ATP_2022">ATP 2022</option>
			      </select>
			    </div>			  
			    <div id="vizPortNumber_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
			    <label for="vizPortNumber" class="col-sm-4 col-form-label text-left">Viz Port Number 
			    	<i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
			    <div class="col-sm-6 col-md-6">
		             <input type="text" id="vizPortNumber" name="vizPortNumber" value="${session_Configurations.portNumber}"
		             	class="form-control form-control-sm floatlabel"></input>
			    </div>
			  </div>
			  <div id="vizIPAddress_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
			    <label for="vizIPAddress" class="col-sm-4 col-form-label text-left">Viz IP Address 
			    	<i class="fas fa-asterisk fa-sm text-danger" style="font-size: 7px;"></i></label>
			    <div class="col-sm-6 col-md-6">
		             <input type="text" id="vizIPAddress" name="vizIPAddress" value="${session_Configurations.ipAddress}"
		             		class="form-control form-control-sm floatlabel" value="localhost"></input>
			    </div>
			  </div>
			  </div>
			  &nbsp;
		    <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
		  		name="load_scene_btn" id="load_scene_btn" onclick="processUserSelection(this)">
		  		<i class="fas fa-film"></i> Load Scene</button>
	       </div>
	    </div>
       </div>
    </div>
  </div>
</div>
</form:form>
</body>
</html>