/*
function sendRequest(method) {
    return fetch('http://localhost:8080/rest/all').then(response => {
        return response.json()
    })
}

sendRequest('GET').then(data => console.log(data)).catch(err => console.log(err))*/

$(document).ready (printUsers());

function printUsers() {
    var responce = fetch('http://localhost:8080/rest/all')
        .then((response) => {
            response.json().then((data) => {
                data.forEach(function (r) {
                    var roles = r.roles;
                    var stringRoles = '';
                    roles.forEach(function (item) {
                        stringRoles += item.role + ' ';
                    })
                    var html = '<tr id='+r.id+'>' +
                        '<td>' + r.id + '</td>' +
                        '<td>' + r.firstName + '</td>' +
                        '<td>' + r.lastName + '</td>' +
                        '<td>' + r.email + '</td>' +
                        '<td>' + stringRoles + '</td>' +
                        '<td>' +
                        '<button type="button" class="btn btn-info" data-toggle="modal" data-target="#editModalCenter" onclick="editUser(this)">' +
                        'Edit' +
                        '</button>' +
                        '</td>' +
                        '<td>' +
                        '<button type="button"  class="btnDelete btn btn-danger" data-toggle="modal" data-target="#deleteModalCenter" onclick="deleteRow(this)">' +
                        'Delete' +
                        '</button>' +
                        '</td>' +
                        '</tr>';
                    $('#usersTable').append(html);
                });
            });
        });
}