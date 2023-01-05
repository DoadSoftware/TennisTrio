var match_data;
function processWaitingButtonSpinner(whatToProcess) 
{
	switch (whatToProcess) {
	case 'START_WAIT_TIMER': 
		$('.spinner-border').show();
		$(':button').prop('disabled', true);
		break;
	case 'END_WAIT_TIMER': 
		$('.spinner-border').hide();
		$(':button').prop('disabled', false);
		break;
	}
	
}
function initialiseForm(whatToProcess,dataToProcess)
{
	switch (whatToProcess) {
	case 'initialise':
		processUserSelection($('#select_broadcaster'));
		break;
	}
}
function processUserSelectionData(whatToProcess,dataToProcess){
	//alert(whatToProcess);
	switch (whatToProcess) {
	case 'LOGGER_FORM_KEYPRESS':
		switch (dataToProcess) {
		case 32:
			processTennisProcedures('CLEAR-ALL');
			break;
			
		case 112:
			//processTennisProcedures('TRIO_ORDER_OF_PLAY');
			addItemsToList('TRIO_ORDER_OF_PLAY-OPTION',null)
			break;		
		}
		
		break;
	}
}
function processUserSelection(whichInput)
{	
	switch ($(whichInput).attr('name')) {	
	case 'animateout_graphic_btn':
		is_scorebug_on_screen = false;
		if(confirm('It will Also Delete Your Preview from Directory...\r\n \r\nAre You Sure To Animate Out? ') == true){
			processTennisProcedures('ANIMATE-OUT');	
		}
		break;
	case 'clearall_graphic_btn':
		if(confirm('Are You Sure To Clear All Scenes? ') == true){
			$('#select_graphic_options_div').empty();
			document.getElementById('select_graphic_options_div').style.display = 'none';
			//$("#captions_div").show();
			$("#main_captions_div").show();
			
			processTennisProcedures('CLEAR-ALL');
		}
		break;
		
	case 'cancel_graphics_btn':
		$('#select_graphic_options_div').empty();
		document.getElementById('select_graphic_options_div').style.display = 'none';
		//$("#captions_div").show();
		$("#main_captions_div").show();
		
		break;
	case 'save_order_of_play_btn':
		processTennisProcedures('TRIO_ORDER_OF_PLAY');
		break;
	
	case 'load_scene_btn':
		/*if(checkEmpty($('#vizIPAddress'),'IP Address Blank') == false
			|| checkEmpty($('#vizPortNumber'),'Port Number Blank') == false) {
			return false;
		}*/
      	document.initialise_form.submit();
		break;
	}
}
function processTennisProcedures(whatToProcess)
{
	var valueToProcess;
	switch(whatToProcess) {
	case 'TRIO_ORDER_OF_PLAY':
		valueToProcess = $('#saveOrderofplayfile').val();
		break;
	}

	$.ajax({    
        type : 'Get',     
        url : 'processTennisProcedures.html',     
        data : 'whatToProcess=' + whatToProcess + '&valueToProcess=' + valueToProcess, 
        dataType : 'json',
        success : function(data) {
        	switch(whatToProcess) {
			case 'TRIO_ORDER_OF_PLAY': 
				if (data.status.toUpperCase() == 'SUCCESSFUL') {
					if(confirm('Want to Save file?') == true){
						     
						$('#select_graphic_options_div').empty();
						document.getElementById('select_graphic_options_div').style.display = 'none';
						$("#main_captions_div").show();
						
			        	switch(whatToProcess) {
						case 'TRIO_ORDER_OF_PLAY':
							processTennisProcedures('SAVE_TRIO_ORDER_OF_PLAY');
							break;
						}
					}
					
				} else {
					alert(data.status);
				}
				break;
        	}
			processWaitingButtonSpinner('END_WAIT_TIMER');
	    },    
	    error : function(e) {    
	  	 	console.log('Error occured in ' + whatToProcess + ' with error description = ' + e);     
	    }    
	});
}
function addItemsToList(whatToProcess, dataToProcess)
{
	var select,option,header_text,div,table,tbody,row;
	
	switch(whatToProcess){
		case 'TRIO_ORDER_OF_PLAY-OPTION':
			switch ($('#select_broadcaster').val().toUpperCase()){
				case 'ATP_2022':
					$('#select_graphic_options_div').empty();
	
					header_text = document.createElement('h6');
					header_text.innerHTML = 'Select Graphic Options';
					document.getElementById('select_graphic_options_div').appendChild(header_text);
					
					table = document.createElement('table');
					table.setAttribute('class', 'table table-bordered');
							
					tbody = document.createElement('tbody');
			
					table.appendChild(tbody);
					document.getElementById('select_graphic_options_div').appendChild(table);
					
					row = tbody.insertRow(tbody.rows.length);
					switch(whatToProcess){
						case 'TRIO_ORDER_OF_PLAY-OPTION':
							select = document.createElement('input');
							select.type = "text";
							select.id = 'saveOrderofplayfile';
							select.value = '';
							
							row.insertCell(0).appendChild(select);
							
							option = document.createElement('input');
						    option.type = 'button';
							option.name = 'save_order_of_play_btn';
							option.value = 'Save Order Of Play';
						    option.id = option.name;
						    option.setAttribute('onclick',"processUserSelection(this)");
						    
						    div = document.createElement('div');
						    div.append(option);
						
							option = document.createElement('input');
							option.type = 'button';
							option.name = 'cancel_graphics_btn';
							option.id = option.name;
							option.value = 'Cancel';
							option.setAttribute('onclick','processUserSelection(this)');
						
						    div.append(option);
						    
						    row.insertCell(1).appendChild(div);
						    
							document.getElementById('select_graphic_options_div').style.display = '';
							break;
					}
					break;
			}
			break;
	}

	
}
function checkEmpty(inputBox,textToShow) {

	var name = $(inputBox).attr('id');
	
	document.getElementById(name + '-validation').innerHTML = '';
	document.getElementById(name + '-validation').style.display = 'none';
	$(inputBox).css('border','');
	if(document.getElementById(name).value.trim() == '') {
		$(inputBox).css('border','#E11E26 2px solid');
		document.getElementById(name + '-validation').innerHTML = textToShow + ' required';
		document.getElementById(name + '-validation').style.display = '';
		document.getElementById(name).focus({preventScroll:false});
		return false;
	}
	return true;	
}