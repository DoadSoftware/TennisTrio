<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>

  <meta charset="utf-8" name="viewport" content="width=device-width, initial-scale=1">
  <title>Output Screen</title>
	
  <script type="text/javascript" src="<c:url value="/webjars/jquery/3.6.0/jquery.min.js"/>"></script>
  <script type="text/javascript" src="<c:url value="/webjars/bootstrap/5.1.3/js/bootstrap.min.js"/>"></script>
  <script type="text/javascript" src="<c:url value="/resources/javascript/index.js"/>"></script>
  
  <link rel="stylesheet" href="<c:url value="/webjars/bootstrap/5.1.3/css/bootstrap.min.css"/>"/>  
  <link href="<c:url value="/webjars/font-awesome/6.0.0/css/all.css"/>" rel="stylesheet">
  <script type="text/javascript">
	$(document).on("keydown", function(e){
		if (e.which >= 112 && e.which <= 123) { // Suppress default behaviour of F1 to F12
			e.preventDefault();
		}
		processUserSelectionData('LOGGER_FORM_KEYPRESS',e.which);
	});
  </script>  
</head>
<body>
<form:form name="output_form" autocomplete="off" action="POST">
<div class="content py-5" style="background-color: #EAE8FF; color: #2E008B">
  <div class="container">
	<div class="row">
	 <div class="col-md-8 offset-md-2">
       <span class="anchor"></span>
         <div class="card card-outline-secondary">
           <div class="card-header">
             <h3 class="mb-0">Output</h3>
           </div>
          <div class="card-body">
			  <div id="select_graphic_options_div" style="display:none;">
			  </div>
		<div id="main_captions_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
			  <div id="captions_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
			    <label class="col-sm-4 col-form-label text-left">IP Address: ${session_selected_ip} </label>
			    <label class="col-sm-4 col-form-label text-left">Port Number: ${session_port} </label>
			    <label class="col-sm-4 col-form-label text-left">Broadcaster: ${session_selected_broadcaster} </label>
			  </div>
			<!--  <div class="left">
			<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
		  		name="scorebug_graphic_btn" id="scorebug_graphic_btn" onclick="processUserSelection(this)"> Scorebug </button>
		  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
		  		name="scorebugstat_graphic_btn" id="scorebugstat_graphic_btn" onclick="processUserSelection(this)"> Scorebug Stats </button> 
		  	</div>-->
			</div>
	      </div>
	    </div>
       </div>
    </div>
  </div>
</div>
<input type="hidden" name="select_broadcaster" id="select_broadcaster" value="${session_selected_broadcaster}"/>
</form:form>
</body>
</html>