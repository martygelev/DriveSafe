function loadEvents(data) {

    $('#accordion').find('.card').remove().end();

    $.each(data, function () {

        let mockCard = $('#mockCard').clone();
        mockCard.attr("style", "display:block;");
        mockCard.attr("id", this.id);
        mockCard.find('#headingOne')
            .attr('id', 'heading' + this.id)
            .attr('data-target', '#collapse' + this.id)
            .attr('data-controls', 'collapse' + this.id)
            .attr('aria-controls', 'collapse' + this.id)
            .attr('aria-expanded', 'false');

        let time = this.date.toString();

        mockCard.find('.head h6')
            .text(formatDate(new Date(time)) + ' - ' + this.customerCar.car.model.manufacturer.name
                + ' ' + this.customerCar.car.model.name + ' - ' + this.customerCar.customer.name + ' - ' + this.detailedRepairServices.length + ' ' + 'services done');

        mockCard.find('#collapseOne')
            .attr('id', 'collapse' + this.id)
            .attr('aria-labelledby', 'heading' + this.id);

        mockCard.find('.card-body')
            .append('<div class="col-12 col-sm-12 services"></div>');

        $(this.detailedRepairServices).each(function () {
            mockCard.find('.services')
                .append(`<p class="mt-2"><span><strong>${this.repairService.name}</strong></span>: <span>${this.price}$</span></p>`);

            if (this.serviceComment) {
                mockCard.find('.services p:last-child')
                    .append(`<br/><span class="ml-3"><i>${this.serviceComment}</i></span>`)
            }
        });

        if (this.comment) {
            mockCard.find('.services')
                .append(`<p class="mt-3" style="margin-bottom: -5px"><strong>Additional comments: </strong></p><span class="ml-3"><i>${this.comment}</i></span>`);
        }

        if (this.finalized) {
            mockCard.find('.finalizedBtn')
                .css("display", "block")
                .find('.dropdown-menu')
                .attr('data-event', this.id);

            mockCard.find('.getInv').attr("href", "/pdf?event=" + this.id);

            mockCard.find('.emailInv').attr('onclick', 'sendEmailWithInvoice(' + `${this.id}` + ');event.preventDefault();');

            mockCard.find('.notFinalizedBtn').html('');


        } else {
            mockCard.find('.notFinalizedBtn')
                .css("display", "block")
                .find('.dropdown-menu')
                .attr('data-event', this.id);

            mockCard.find('.markFinalized').attr('onclick', 'markFinalized(' + `${this.id}` + ');event.preventDefault();');
            mockCard.find('.markAndEmail').attr('onclick', 'markAndEmail(' + `${this.id}` + ');event.preventDefault();');

            mockCard.find('.finalizedBtn').html('');
        }


        mockCard.prependTo('#accordion');

    })
}

function formatDate(date) {
    let monthNames = [
        "January", "February", "March",
        "April", "May", "June", "July",
        "August", "September", "October",
        "November", "December"
    ];

    let day = date.getDate();
    let monthIndex = date.getMonth();
    let year = date.getFullYear();

    return day + ' ' + monthNames[monthIndex] + ' ' + year;
}

sendEmailWithInvoice = function (id) {

    $.ajax({
        url: '/pdf/email?event=' + id,
        type: 'GET'
    });

    $.notify({
        message: "Email sent successfully to customer."
    }, {
        type: 'success',
        allow_dismiss: false,
        delay: 7000
    });

};