function initializeSelect2() {
    var editFlag = false;

    if (localStorage.getItem("school").length > 2) {
        $("#schoolsList").select2({
            tags: true
        }).val(localStorage.getItem("school")).trigger("change");

        editFlag = true;
    }

    if (localStorage.getItem("state").length > 1) {
        $("#statesList").select2().val(localStorage.getItem("state")).trigger("change");

        editFlag = true;
    }
    if (localStorage.getItem("fields").length > 2) {
        fields = "";
        fieldArray = localStorage.getItem("fields").split(",");
        $("#fieldsList").select2().val(fieldArray).trigger("change");

        editFlag = true;
    }


    if (localStorage.getItem("pastFields").length > 2) {
        pastFields = "";
        pastFieldArray = localStorage.getItem("pastFields").split(",");
        $("#pastFieldsList").select2().val(pastFieldArray).trigger("change");

        editFlag = true;
    }

    if (localStorage.getItem("certifications").length > 2) {
        certifications = "";
        certificationArray = localStorage.getItem("certifications").split(",");
        $("#certificationsList").select2().val(certificationArray).trigger("change");

        editFlag = true;
    }

    if (localStorage.getItem("obtainedCertifications").length > 2) {
        pastCertifications = "";
        pastCertificationArray = localStorage.getItem("obtainedCertifications").split(",");
        $("#pastCertificationsList").select2().val(pastCertificationArray).trigger("change");

        editFlag = true;
    }

    if (localStorage.getItem("pastEducation").length > 2) {
        pastEducation = "";
        pastEducationArray = localStorage.getItem("pastEducation").split(",");
        $("#pastEducationList").select2().val(pastEducationArray).trigger("change");

        editFlag = true;
    }
    if (localStorage.getItem("completionDate").length > 2) {
        $("input[name=completionDate]")[0].setAttribute("value", localStorage.getItem("completionDate"));

        editFlag = true;
    }
    if (localStorage.getItem("workExperience").length > 2) {
        var arr = $("input[name=workexperience]");
        for (var i = 0; i < arr.length; i++) {
            if (arr[i].value == localStorage.getItem("workExperience")) {
                arr[i].setAttribute("checked", "true");
            }
        }

        editFlag = true;
    }
    if (localStorage.getItem("veteran").length > 1) {
        arr = $("input[name=veteran]");
        for (var i = 0; i < arr.length; i++) {
            if (arr[i].value == localStorage.getItem("veteran")) {
                arr[i].setAttribute("checked", "true");
            }
        }

        editFlag = true;
    }
    if (localStorage.getItem("employed").length > 1) {
        arr = $("input[name=employed]");
        for (var i = 0; i < arr.length; i++) {
            if (arr[i].value == localStorage.getItem("employed")) {
                arr[i].setAttribute("checked", "true");
            }
        }

    }
    if (localStorage.getItem("relocate").length > 1) {
        arr = $("input[name=relocate]");
        for (var i = 0; i < arr.length; i++) {
            if (arr[i].value == localStorage.getItem("relocate")) {
                arr[i].setAttribute("checked", "true");
            }
        }

        editFlag = true;
    }
    if (localStorage.getItem("additionalInformation").length > 1) {
        $("textarea[name=additionalInformation]")[0].setAttribute("value", localStorage.getItem("additionalInformation"));

        editFlag = true;
    }
    if (localStorage.getItem("authorized").length > 1) {
        arr = $("input[name=authorized]");
        for (var i = 0; i < arr.length; i++) {
            if (arr[i].value == localStorage.getItem("authorized")) {
                arr[i].setAttribute("checked", "true");
            }
        }

        editFlag = true;
    }

    if (editFlag == true) {
        candidateUpdate();
    }
};

function fileSubmit(type) {

    var titles = []
    var arr = $('#schools span[title]');
    for (var i = 0; i < arr.length; i++) {
        titles.push(arr[i].title);
    };
    var string = titles.toString();

    localStorage.setItem("school", string);

    titles = []
    arr = $('#states span[title]');
    for (var i = 0; i < arr.length; i++) {
        titles.push(arr[i].title);
    };
    string = titles.toString();

    localStorage.setItem("state", string);

    titles = []
    arr = $('#fields li[title]');
    for (var i = 0; i < arr.length; i++) {
        titles.push(arr[i].title);
    };
    string = titles.toString();

    localStorage.setItem("fields", string);

    localStorage.setItem("completionDate", $("input[name=completionDate]").val())

    titles = []
    arr = $('#certifications li[title]');
    for (var i = 0; i < arr.length; i++) {
        titles.push(arr[i].title);
    };
    string = titles.toString();

    localStorage.setItem("certifications", string);

    titles = []
    arr = $('#pastFields li[title]');
    for (var i = 0; i < arr.length; i++) {
        titles.push(arr[i].title);
    };
    string = titles.toString();

    localStorage.setItem("pastFields", string);

    titles = []
    arr = $('#obtainedCertifications li[title]');
    for (var i = 0; i < arr.length; i++) {
        titles.push(arr[i].title);
    };
    string = titles.toString();

    localStorage.setItem("obtainedCertifications", string);

    titles = []
    arr = $('#pastEducations li[title]');
    for (var i = 0; i < arr.length; i++) {
        titles.push(arr[i].title);
    };
    string = titles.toString();

    localStorage.setItem("workExperience", $("input[name=workExperience][checked]").val());
    localStorage.setItem("veteran", $("input[name=veteran][checked]").val());
    localStorage.setItem("employed", $("input[name=employed][checked]").val());
    localStorage.setItem("relocate", $("input[name=relocate][checked]").val());
    localStorage.setItem("additionalInformation", $('textarea[name=additionalInformation]').val());

    localStorage.setItem("pastEducation", string);

    if (type == 'resume') {
        document.getElementById('resume-upload').submit();
    } else {
        document.getElementById('picture-upload').submit();
    }
};

// Checks input fields to ensure mandatory fields are filled. Highlights in red // otherwise and blocks submission. If mandatory fields are filled, submits form

function validate() {

    var x;
    var flag = true;

    if ($('#firstName')[0].value.length == 0) {
        x = $('#firstName')[0].parentNode;
        x.className += ' incomplete';
        flag = false;
    }

    if ($('#lastName')[0].value.length == 0) {
        x = $('#lastName')[0].parentNode;
        x.className += ' incomplete';
        flag = false;
    }
    if ($('#email')[0].value.length == 0) {
        x = $('#email')[0].parentNode;
        x.className += ' incomplete';
        flag = false;
    }
    if ($('#phone')[0].value.length == 0) {
        x = $('#phone')[0].parentNode;
        x.className += ' incomplete';
        flag = false;
    }
    if ($('#city')[0].value.length == 0) {
        x = $('#city')[0].parentNode;
        x.className += ' incomplete';
        flag = false;
    }
    if ($('#zip')[0].value.length == 0) {
        x = $('#zip')[0].parentNode;
        x.className += ' incomplete';
        flag = false;
    }
    if ($('#statesList')[0].value.length == 0) {
        x = $('#statesList')[0].parentNode;
        x.className += ' incomplete';
        flag = false;
    }

    if ($('#schoolsList')[0].value.length == 0) {
        x = $('#schoolsList')[0].parentNode;
        x.className += ' incomplete';
        flag = false;
    }

    if ($('#date')[0].value.length == 0) {
        x = $('#date')[0].parentNode.parentNode;
        x.className += ' incomplete';
        flag = false;
    }
    if ($('#fieldsList')[0].value.length == 0) {
        x = $('#fieldsList')[0].parentNode.parentNode;
        x.className += ' incomplete';
        flag = false;
    }

    if ($('#certificationsList')[0].value.length == 0) {
        x = $('#certificationsList')[0].parentNode.parentNode;
        x.className += ' incomplete';
        flag = false;
    }

    if ($('#pastFieldsList')[0].value.length == 0) {
        x = $('#pastFieldsList')[0].parentNode.parentNode;
        x.className += ' incomplete';
        flag = false;
    }

    if ($('#pastCertificationsList')[0].value.length == 0) {
        x = $('#pastCertificationsList')[0].parentNode.parentNode;
        x.className += ' incomplete';
        flag = false;
    }

    var arr = $('input[name="workExperience"]');
    var radioFlag = false;
    for (var i = 0; i < arr.length; i++) {
        if (arr[i].checked) {
            radioFlag = true;
        }
    }

    if (radioFlag == false) {
        arr[0].parentNode.parentNode.parentNode.parentNode.className += ' incomplete';
        flag = false;
    }


    if ($('#pastEducationList')[0].value.length == 0) {
        x = $('#pastEducationList')[0].parentNode.parentNode;
        x.className += ' incomplete';
        flag = false;
    }

    if (flag == false) {
        $('#incompleteError').toggleClass("hidden");
        ga('send', 'event', 'ProfileUpdate', 'Fail');
    }

    if (flag == true) {
        localStorage.removeItem("authorized");
        localStorage.removeItem("additionalInformation");
        localStorage.removeItem("relocate");
        localStorage.removeItem("employed");
        localStorage.removeItem("veteran");
        localStorage.removeItem("workExperience");
        localStorage.removeItem("pastEducation");
        localStorage.removeItem("obtainedCertifications");
        localStorage.removeItem("certifications");
        localStorage.removeItem("pastFields");
        localStorage.removeItem("state");
        localStorage.removeItem("fields");
        localStorage.removeItem("school");
        ga('send', 'event', 'ProfileUpdate', 'Success');
    }

    return flag;
};

//Initiates and populates Select2 defaults with pre-existing values pulled from Candidate profile
$(document).ready(function () {

    if ($("input[name=school]").val().length > 2) {
        $("#schoolsList").select2({
            tags: true
        }).val($("input[name=school]").val()).trigger("change");
    } else {
        $("#schoolsList").select2({
            tags: true
        }).val(" ").trigger("change");
    }

    if ($("input[name=state]").val().length > 1) {
        $("#statesList").select2().val($("input[name=state]").val()).trigger("change");
    } else {
        $("#statesList").select2().val(" ").trigger("change");
    }

    fields = "";
    fieldArray = $("input[name=field]").val().split(",");
    $("#fieldsList").select2().val(fieldArray).trigger("change");

    pastFields = "";
    pastFieldArray = $("input[name=pastField]").val().split(",");
    $("#pastFieldsList").select2().val(pastFieldArray).trigger("change");

    certifications = "";
    certificationArray = $("input[name=certification]").val().split(",");
    $("#certificationsList").select2().val(certificationArray).trigger("change");

    pastCertifications = "";
    pastCertificationArray = $("input[name=obtainedCertification]").val().split(",");
    $("#pastCertificationsList").select2().val(pastCertificationArray).trigger("change");

    pastEducation = "";
    pastEducationArray = $("input[name=pastEducation]").val().split(",");
    $("#pastEducationList").select2().val(pastEducationArray).trigger("change");
});

//Phone validation -->
jQuery(function ($) {
    $("#phone").mask("(999) 999-9999");
    $("#alternatePhone").mask("(999) 999-9999");
});

// Script to switch elements to edit mode
function toggleEditContainer() {
    $('#welcome-container').toggleClass("hidden-xs");
    $('#candidate-detail-edit').toggleClass("hidden");
    $('#candidate-detail-view').toggleClass("hidden");
    $('#edit-button').toggleClass("hidden");
    $('#navbar-edit-message').toggleClass("hidden");
    $('#navbar-view-message').toggleClass("hidden");
    $('#profile-card-edit').toggleClass("hidden");
    $('#profile-card-view').toggleClass("hidden");
    $('#submit-button').toggleClass("hidden");
};

//Pulls selected data from Select2 lists and adds it to hidden input fields which //are then sent to POST
function candidateUpdate() {

    var titles = []
    var arr = $('#schools span[title]');
    for (var i = 0; i < arr.length; i++) {
        titles.push(arr[i].title);
    };
    var string = titles.toString();

    $("[name='school']")[0].setAttribute("value", string);

    titles = []
    arr = $('#states span[title]');
    for (var i = 0; i < arr.length; i++) {
        titles.push(arr[i].title);
    };
    string = titles.toString();

    $("[name = 'state']")[0].setAttribute("value", string);

    titles = []
    arr = $('#fields li[title]');
    for (var i = 0; i < arr.length; i++) {
        titles.push(arr[i].title);
    };
    string = titles.toString();

    $("[name='field']")[0].setAttribute("value", string);

    titles = []
    arr = $('#certifications li[title]');
    for (var i = 0; i < arr.length; i++) {
        titles.push(arr[i].title);
    };
    string = titles.toString();

    $("[name='certification']")[0].setAttribute("value", string);

    titles = []
    arr = $('#pastFields li[title]');
    for (var i = 0; i < arr.length; i++) {
        titles.push(arr[i].title);
    };
    string = titles.toString();

    $("[name='pastField']")[0].setAttribute("value", string);

    titles = []
    arr = $('#obtainedCertifications li[title]');
    for (var i = 0; i < arr.length; i++) {
        titles.push(arr[i].title);
    };
    string = titles.toString();

    $("[name='obtainedCertification']")[0].setAttribute("value", string);

    titles = []
    arr = $('#pastEducations li[title]');
    for (var i = 0; i < arr.length; i++) {
        titles.push(arr[i].title);
    };
    string = titles.toString();

    $("[name='pastEducation']")[0].setAttribute("value", string);

};

window.onload = function () {

    if (document.getElementById("hiddenResume").value == "true") {
        toggleEditContainer();
    }
};

(function ($) {
    /**
     * polyfill for html5 form attr
     */

    // detect if browser supports this
    var sampleElement = $('[form]').get(0);
    var isIE11 = !(window.ActiveXObject) && "ActiveXObject" in window;
    if (sampleElement && window.HTMLFormElement && sampleElement.form instanceof HTMLFormElement && !isIE11) {
        // browser supports it, no need to fix
        return;
    }

    /**
     * Append a field to a form
     *
     */
    $.fn.appendField = function (data) {
        // for form only
        if (!this.is('form')) return;

        // wrap data
        if (!$.isArray(data) && data.name && data.value) {
            data = [data];
        }

        var $form = this;

        // attach new params
        $.each(data, function (i, item) {
            $('<input/>')
                .attr('type', 'hidden')
                .attr('name', item.name)
                .val(item.value).appendTo($form);
        });

        return $form;
    };

    /**
     * Find all input fields with form attribute point to jQuery object
     * 
     */
    $('form[id]').submit(function (e) {
        // serialize data
        var data = $('[form=' + this.id + ']').serializeArray();
        // append data to form
        $(this).appendField(data);
    }).each(function () {
        var form = this,
            $fields = $('[form=' + this.id + ']');

        $fields.filter('button, input').filter('[type=reset],[type=submit]').click(function () {
            var type = this.type.toLowerCase();
            if (type === 'reset') {
                // reset form
                form.reset();
                // for elements outside form
                $fields.each(function () {
                    this.value = this.defaultValue;
                    this.checked = this.defaultChecked;
                }).filter('select').each(function () {
                    $(this).find('option').each(function () {
                        this.selected = this.defaultSelected;
                    });
                });
            } else if (type.match(/^submit|image$/i)) {
                $(form).appendField({
                    name: this.name,
                    value: this.value
                }).submit();
            }
        });
    });


})(jQuery);

/* Mobile only scripts */

function viewProfile() {
    $('#profile-card-view').removeClass('hidden-xs');
    $('#candidate-detail-view').removeClass('hidden-xs');
    $('#profile-card-edit').addClass('hidden');
    $('#candidate-detail-edit').addClass('hidden');
    $('#jobs-container').addClass('hidden-xs');
    $('#account-container').addClass('hidden');
    $('#welcome-container').addClass('hidden-xs');
    $('#submit-button').addClass('hidden');
};

function editProfile() {
    $('#profile-card-view').addClass('hidden');
    $('#candidate-detail-view').addClass('hidden');
    $('#profile-card-edit').removeClass('hidden');
    $('#candidate-detail-edit').removeClass('hidden');
    $('#jobs-container').addClass('hidden-xs');
    $('#account-container').addClass('hidden');
    $('#welcome-container').addClass('hidden-xs');
    $('#submit-button').removeClass('hidden');
};

function viewJobs() {
    $('#profile-card-view').addClass('hidden');
    $('#candidate-detail-view').addClass('hidden');
    $('#profile-card-edit').addClass('hidden');
    $('#candidate-detail-edit').addClass('hidden');
    $('#jobs-container').removeClass('hidden-xs');
    $('#account-container').addClass('hidden');
    $('#welcome-container').addClass('hidden-xs');
    $('#submit-button').addClass('hidden');
};

function settings() {
    $('#profile-card-view').addClass('hidden');
    $('#candidate-detail-view').addClass('hidden');
    $('#profile-card-edit').addClass('hidden');
    $('#candidate-detail-edit').addClass('hidden');
    $('#jobs-container').addClass('hidden-xs');
    $('#account-container').removeClass('hidden');
    $('#welcome-container').addClass('hidden-xs');
    $('#submit-button').addClass('hidden');
};

function viewHelp() {
    $('#profile-card-view').addClass('hidden');
    $('#candidate-detail-view').addClass('hidden');
    $('#profile-card-edit').addClass('hidden');
    $('#candidate-detail-edit').addClass('hidden');
    $('#jobs-container').addClass('hidden-xs');
    $('#account-container').addClass('hidden');
    $('#welcome-container').removeClass('hidden-xs');
    $('#submit-button').addClass('hidden');
};