let stompClient = null;

const setConnected = (connected) => {
  $("#connect").prop("disabled", connected);
  $("#disconnect").prop("disabled", !connected);
  if (connected) {
    $("#userTable").show();
  } else {
    $("#userTable").hide();
  }
  $("#name").html("");
  $("#login").html("");
  $("#password").html("");
}

const connect = () => {
  stompClient = Stomp.over(new SockJS('/gs-guide-websocket'));
  stompClient.connect({}, (frame) => {
    setConnected(true);
    console.log('Connected: ' + frame);
    stompClient.subscribe('/topic/response/addUser', showNewUserId);
    stompClient.subscribe('/topic/response/allUsers', showAllUsers);
  });
}

const disconnect = () => {
  if (stompClient !== null) {
    stompClient.disconnect();
  }
  setConnected(false);
  console.log("Disconnected");
}

const addUser = () => stompClient.send("/app/admin.addUser", {}, JSON.stringify({
                                                                                 'id': '',
                                                                                 'name': $("#name").val(),
                                                                                 'login': $("#login").val(),
                                                                                 'password': $("#password").val()
                                                                                 }))

const allUsers = () => stompClient.send("/app/admin.allUsers", {}, {})

function showNewUserId (payload){
    document.getElementById("addUserId").innerHTML = "добавлен пользователь с id: " + payload.body;
    allUsers();

}

function showAllUsers(payload) {
    var users = JSON.parse(payload.body);
    $("#userTable").empty();
    for(var i =0 ;i < users.length;i++){
        var tr ="<tr>";
        var td1 = "<td>"+users[i].id+"</td>";
        var td2 = "<td>"+users[i].name+"</td>";
        var td3 = "<td>"+users[i].login+"</td>";
        var td4 = "<td>"+users[i].password+"</td>";
        var tre = "</tr>";
        $("#userTable").append(tr+td1+td2+td3+td4+tre);
    }
}

$(function () {
  $("form").on('submit', (event) => {
    event.preventDefault();
  });
  $("#connect").click(connect);
  $("#disconnect").click(disconnect);
  $("#addUser").click(addUser);
  $("#refreshAllUsers").click(allUsers);

});
