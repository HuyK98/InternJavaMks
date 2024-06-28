/**
 * Process event: upload button is clicked & uploaded file is available
 */
$(function () {
    $('#attachment').on('change', function() {
        // Auto submit form
        $('#frm_searchimage').submit();
    });
    

});


/**
 * Process event: form is submitted
 */

$(function () {

  $('#frm_searchimage').submit(function(e) {
      e.preventDefault();
      
      var frm = $('#frm_searchimage');
      var frmData = new FormData(this);

          
      $.ajax({
          // url : _ctx + 'searchmos/upload',
          url : _ctx + frm.attr('action'),
          type : frm.attr('method'),
          enctype : frm.attr('enctype'),
          data : frmData,
          processData : false,
          contentType : false,
          success : function(result) {
            if (result) {
                // console.log(result);
                updateOutput(result);
            } 
              // alert("Save OK.");
          },
          error : function() {
              alert("Process uploaded file failed.");
          }
      });
  });

});

function updateOutput(content) {
    alert("To be updated the output");
}

