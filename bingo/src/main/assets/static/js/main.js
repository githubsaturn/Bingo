function customFunctionClicked(functionName, uuid) {
    var inputMsg = {methodname: functionName};
    
    var args = $("[id^="+uuid+"]");
    //alert ('__'+args.length+'__'+args.size);
    //return;
    
for (var i = 0; i < args.length; i++) {
    var myId = args[i].id;
    paramName = myId.substring(41, myId.length);
    inputMsg[paramName] = $('#'+myId).val();
}
    
    
    
    $.ajax({
    type: 'POST',
        url:"../customfunc",
        data: inputMsg,
        dataType:'application/json',
         success: function(result){
        //$("span").html(result);
        alert(result );
    }});
}