function initializeSelect2() {
    for (var i = 0; i < arguments.length; i++) {
        $(arguments[i]).select2();
    }
};

function submitSearchFormJob(jobType) {
    $('input[name=jobType]')[0].setAttribute('value', jobType);
    document.getElementById('search-form').submit();
};

function submitSearchFormLocation(location) {
    $('input[name=location]')[0].setAttribute('value', location);
    document.getElementById('search-form').submit();
};

function submitSearchFormSort(sort) {
    $('input[name=sort]')[0].setAttribute('value', sort);
    document.getElementById('search-form').submit();
};