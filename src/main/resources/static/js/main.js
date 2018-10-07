function updateLocale(locale) {
	window.location.replace('login?lang=' + locale);
}

$(document).ready(function() {
    $("#locales").change(function () {
        var selectedOption = $('#locales').val();
        if (selectedOption !== ''){
            window.location.replace('login?lang=' + selectedOption);
        }
    });
});