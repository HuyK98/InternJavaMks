$(function () {
    $('#attachment').on('change', function() {
        // Enable the submit button when a file is selected
        $('#btnSubmit').prop('disabled', false);

        // Auto submit form
        $('#frm_searchmos').submit();
    });

    $('#frm_searchmos').submit(function(e) {
        e.preventDefault();

        var frm = $(this);
        var formData = new FormData(this);

        $.ajax({
            url: frm.attr('action'),
            type: frm.attr('method'),
            data: formData,
            processData: false,
            contentType: false,
            success: function(response) {
                $('#resultText').html(response);
            }
        });
    });
});
