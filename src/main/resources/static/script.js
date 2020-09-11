$(document).ready(printUsers());

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
                    var html = '<tr id=' + r.id + '>' +
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

function addUser() {
    var roles = '?roles=';
    var newRoles = $('#newRoles').val();
    newRoles.forEach(function (item) {
        roles += item + ',';
    })
    var roles1 = roles.substr(0, roles.length - 1);
    var jsonVar = {
        firstName: document.getElementById("firstName").value,
        lastName: document.getElementById("lastName").value,
        email: document.getElementById("email").value,
        password: document.getElementById("password").value
    };
    const response = fetch('http://localhost:8080/rest/add' + roles1, {
        method: 'POST',
        body: JSON.stringify(jsonVar),
        headers: {
            'Content-Type': 'application/json'
        }
    });
    alert("New User added");
    document.getElementById('addForm').reset();
    document.getElementById('table').click();
    $("#usersTable > tbody").empty();
    printUsers();
}

function editUser(o) {
    document.getElementById('editForm').reset();
    var id = $(o).closest('tr').find('td').eq(0).text();
    var url = 'http://localhost:8080/rest/all/' + id;
    fetch(url)
        .then((response) => {
            response.json().then((data) => {
                $('#idEdit').val(data.id);
                $('#firstNameEdit').val(data.firstName);
                $('#lastNameEdit').val(data.lastName);
                $('#emailEdit').val(data.email);
                $('#passwordEdit').val(data.password);
                var roles = data.roles;
                var newRoles = [];
                $('#newRoles option').each(function () {
                    newRoles[$(this).val()] = $(this).val();
                });
                roles.forEach(function (item) {
                    if (newRoles.includes(String(item.id))) {
                        $('#editRoles option[id=' + item.id + ']').prop('selected', true);
                    }
                })
            });
        });
}

function updateUser() {
    var roles = '?roles=';
    var newRoles = $('#editRoles').val();
    newRoles.forEach(function (item) {
        roles += item + ',';
    })
    var roles1 = roles.substr(0, roles.length - 1);
    var jsonVar = {
        id: document.getElementById("idEdit").value,
        firstName: document.getElementById("firstNameEdit").value,
        lastName: document.getElementById("lastNameEdit").value,
        email: document.getElementById("emailEdit").value,
        password: document.getElementById("passwordEdit").value
    };
    var response = fetch('http://localhost:8080/rest/edit' + roles1, {
        method: 'PUT',
        body: JSON.stringify(jsonVar),
        headers: {
            'Content-Type': 'application/json'
        }
    });
    alert("Data successfully updated");
    $("#usersTable > tbody").empty();
    printUsers();
}

function deleteRow(o) {
    userId = $(o).closest('tr').find('td').eq(0).text();
    document.getElementById('deleteForm').reset();
    var url = 'http://localhost:8080/rest/all/' + userId;
    fetch(url)
        .then((response) => {
            response.json().then((data) => {
                $('#idDelete').val(data.id);
                $('#firstNameDelete').val(data.firstName);
                $('#lastNameDelete').val(data.lastName);
                $('#emailDelete').val(data.email);
                var roles = data.roles;
                console.log(roles);
                var newRoles = [];
                $('#newRoles option').each(function () {
                    newRoles[$(this).val()] = $(this).val();
                });
                console.log(newRoles)
                roles.forEach(function (item) {
                    if (newRoles.includes(String(item.id))) {
                        $('#deleteRoles option[id=' + String(Number(item.id + 2)) + ']').prop('selected', true);
                    }
                })
            });
        });
};

function deleteUser() {
    var url = 'http://localhost:8080/rest/delete/' + userId;
    fetch(url, {
        method: 'DELETE',
    })
        .then(res => res.text())
        .then(res => console.log(res))
    var table = document.getElementById("usersTable");
    var selector = "tr[id='" + userId + "']";
    var row = table.querySelector(selector);
    row.parentElement.removeChild(row);
}