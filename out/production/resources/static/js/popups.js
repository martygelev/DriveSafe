function submitNewService() {
    let name = $('#newServiceForm #newServiceName').val();
    let price = Number($('#newServiceForm #newServicePrice').val());

    let service = {
        "id": `-1`,
        "name": `${name}`,
        "price": `${price}`
    };

    $.ajax({
        url: '/r-services/new',
        type: 'POST',
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(service),
        success: function () {
            $.notify({
                message: `Service created successfully.`
            }, {
                type: 'success',
                delay: 6000
            });
        },
        error: function () {
            $.notify({
                message: `Error creating new service.`
            }, {
                type: 'danger',
                delay: 6000
            });
        }
    })
    $('#newServicePopUp').popup('hide');
}

window.submitNewService = submitNewService;

function submitNewCustomer() {
    $.ajax({
        type: 'POST',
        url: '/r-customers/new',
        data: {email: $('#email').val(), name: $('#name').val(), phone: $('#phone').val()},
        dataType: "json",
        success: function () {
            $.notify({
                message: 'New customer created successfully.'
            }, {
                type: 'success',
                delay: 6000
            });

        },
        error: function () {
            $.notify({
                message: `${data.responseJSON.message}`
            }, {
                type: 'danger',
                delay: 6000
            });
        }
    });
    $('#newCustomerPopup').popup('hide');

}

window.submitNewCustomer = submitNewCustomer;

function assignNewCar() {
    let $vin = $('#vin');
    let $licensePlate = $('#licensePlate');

    let reg = {
        customerId: $('#assignNewCarPopup #customerSelect option:selected').val(),
        modelId: $('#assignNewCarPopup #modelSelect option:selected').val(),
        vin: $vin.val(),
        licensePlate: $licensePlate.val()
    };


    $.ajax({
        url: '/car/prepare',
        data: reg,
        dataType: "json",
        type: 'POST',
        async: false,
        success: function (data) {
            // fillMake();
            // fillCustomers();
            $.notify({
                message: `${data.message}`
            }, {
                type: 'success',
                delay: 6000
            });
        },
        error: function (data) {
            $.notify({
                message: `${data.responseJSON.message}`
            }, {
                type: 'danger',
                delay: 6000
            });
        },
        complete: function () {
            console.log("out")
            $('#assignNewCarPopup').popup('hide');

            $(':input', '#assignNewCarPopup')
                .not(':button, :submit, :reset, :hidden')
                .val('')
                .prop('checked', false)
                .prop('selected', false);
        }
    });
}

window.assignNewCar = assignNewCar;


$(document).ready(function () {


    $('#assignNewCarPopup').popup({
        transition: 'all 0.3s'
    });

    $('#newCustomerPopup').popup({
        transition: 'all 0.3s'
    });

    let $email = $('#email');
    let $name = $('#name');
    let $phone = $('#phone');

    $('#userForm input').change(function () {

        if ($email.val().length > 5 &&
            $email.val().length < 40 &&
            isEmail($email.val()) &&
            $name.val().length > 5 &&
            $name.val().length < 25 &&
            $phone.val().length > 10 &&
            $phone.val().length < 30 &&
            isPhone($phone.val())) {
            $('#userForm #regBtn').prop('disabled', false);
        } else {
            $('#userForm #regBtn').prop('disabled', true);

        }
    })

    $('#userForm #phone').keyup(function () {
        if ($(this).val().length < 10 || $(this).val().length > 30 || !isPhone($(this).val())) {
            $('#phoneError').show();
            $(this).css("border-color", "red");
        } else {
            $('#phoneError').hide();
            $(this).css("border-color", "");
        }
    })
    $('#userForm #name').keyup(function () {
        if ($(this).val().length < 5 || $(this).val().length > 25) {
            $('#nameError').show();
            $(this).css("border-color", "red");
        } else {
            $('#nameError').hide();
            $(this).css("border-color", "");
        }
    })
    $('#userForm #email').keyup(function () {
        if ($(this).val().length < 5 || $(this).val().length > 40 || !isEmail($(this).val())) {
            $('#emailError').show();
            $(this).css("border-color", "red");
        } else {
            $('#emailError').hide();
            $(this).css("border-color", "");
        }
    })


    $('#assignNewCarPopup').popup({
        onopen: function () {
            let makeSelect = $("#assignNewCarPopup #makeSelect");
            let modelSelect = $("#assignNewCarPopup #modelSelect");
            let customerSelect = $('#assignNewCarPopup #customerSelect');

            fillMake();

            fillCustomers();

            function fillCustomers() {
                customerSelect.find('option').remove().end();

                $.ajax({
                    type: 'GET',
                    url: '/r-customers/get',
                    dataType: 'json',
                    success: function (data) {
                        $('#assignNewCarPopup #customerSelect').append('<option value="-1" selected hidden disabled>Choose a customer</option>');

                        $.each(data, function () {
                            $('#assignNewCarPopup #customerSelect').append($("<option />").val(this.id).text(this.name));
                        });
                    }
                })
            }

            function fillMake() {
                makeSelect.find('option').remove().end();
                let make = $.parseJSON($.ajax({
                    url: '/make',
                    dataType: 'json',
                    async: false
                }).responseText);

                $('#assignNewCarPopup #makeSelect').append('<option value="-1" selected hidden disabled>Choose a make</option>');
                $.each(make, function () {
                    makeSelect.append($("<option/>").val(this.name).text(this.name));
                })
            }

            makeSelect.change(function () {
                modelSelect.find('option').remove().end();

                let model = $.parseJSON($.ajax({
                    url: '/model/' + makeSelect.val(),
                    dataType: 'json',
                    async: false
                }).responseText);

                $('#assignNewCarPopup #modelSelect').append('<option value="-1" selected hidden disabled>Choose a model</option>');

                $.each(model, function () {
                    modelSelect.append($("<option/>").val(this.id).text(this.name + ' - ' + this.year.substr(0, 4)));
                })
            });


        }
    });


    $('#assignNewCarPopup form').change(function () {
        if (
            $('#assignNewCarPopup #vin').val().length === 17
            && ($('#assignNewCarPopup #licensePlate').val().length >= 8
            || $('#assignNewCarPopup #licensePlate').val().length <= 10)
            && $('#assignNewCarPopup #customerSelect option:selected').val() !== -1
            && $('#assignNewCarPopup #makeSelect option:selected').val() !== -1
            && $('#assignNewCarPopup #modelSelect option:selected').val() !== -1) {
            $('#assignNewCarPopup #createCarBtnSave').prop('disabled', false);
        } else
            $('#assignNewCarPopup #createCarBtnSave').prop('disabled', true);
    })

    $('#assignNewCarPopup #vin').keyup(function () {
        if ($(this).val().length !== 17) {
            $('#vinError').show();
            $(this).css("border-color", "red");
        } else {
            $('#vinError').hide();
            $(this).css("border-color", "");
        }
    })

    $('#assignNewCarPopup #licensePlate').keyup(function () {
        if ($(this).val().length < 8 || $(this).val().length > 10) {
            $('#licenseError').show();
            $(this).css("border-color", "red");
        } else {
            $('#licenseError').hide();
            $(this).css("border-color", "");
        }
    })


    function isEmail(email) {
        let regex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
        return regex.test(email);
    }

    function isPhone(phone) {
        let regex = /^\d+$/;
        return regex.test(phone);
    }

    $("#menu-toggle").click(function (e) {
        e.preventDefault();
        $("#wrapper").toggleClass("toggled");
    });

    $('#newServicePopUp').popup({
        transition: 'all 0.3s'
    });

    $('#editCustomerPopUp').popup({
        transition: 'all 0.3s'
    });


});

