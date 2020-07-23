var usernameInputEl = document.querySelector("#username");
var firstnameInputEl = document.querySelector("#firstname");
var lastnameInputEl = document.querySelector("#lastname");

var connectBtnEl = document.querySelector('#connect');
var disconnectBtnEl = document.querySelector('#disconnect');
var usernameListEl = document.querySelector("#userList");
var messageBoardEl = document.querySelector('#message-board');
var messageInputEl = document.querySelector('#message');
var sendBtnEl = document.querySelector('#send');

var chatToEl = document.querySelector('#chatTo');

var chatTo = '';

var chatRoom = {};

var socket = undefined;

connectBtnEl.onclick = connect;
disconnectBtnEl.onclick = disconnect;

function connect() {

	socket = new WebSocket("ws://" + location.host + "/Forum/chat?username="
			+ usernameInputEl.value + "&firstname=" + firstnameInputEl.value
			+ "&lastname=" + lastnameInputEl.value);

	socket.onopen = socketOnOpen;
	socket.onmessage = socketOnMessage;
	socket.onclose = socketOnClose;
}

function disconnect() {

	socket.close();
	socket = undefined;
}

function socketOnOpen(e) {
	connectBtnEl.disabled = true;
	disconnectBtnEl.disabled = false;
}

function socketOnMessage(e) {
	var eventName = e.data.substr(0, e.data.indexOf("|"));
	var data = e.data.substr(e.data.indexOf("|") + 1);

	var fn;
	if (eventName == 'newUser')
		fn = newUser;
	else if (eventName == 'removeUser')
		fn = removeUser;
	else if (eventName == 'message')
		fn = getMessage;

	fn.apply(null, data.split('|'));
}

function socketOnClose(e) {
	connectBtnEl.disabled = false;
	disconnectBtnEl.disabled = true;
	messageBoardEl.innerHTML = '';
	chatToEl.innerHTML = '';
	usernameListEl.innerHTML = '';
}

function newUser() {
	
	if (arguments.length == 1 && arguments[0] == "")
		return;
	var usernameList = arguments;

	var documentFragment = document.createDocumentFragment();
	for (var i = 0; i < usernameList.length; i++) {

		var userdata = usernameList[i].split("#");
		var username = userdata[0];
		var firstname = userdata[1];
		var lastname = userdata[2];
		var liEl = document.createElement("li");
		liEl.id = username;
		//liEl.textContent = firstname + " " + lastname;
		liEl.textContent = username;
		liEl.onclick = chatToFn(username, firstname + " " + lastname);
		liEl.classList.add('hoverable');
		documentFragment.appendChild(liEl);
	}
	;
	usernameListEl.appendChild(documentFragment);
}

function getMessage(sender, message, to) {
	to = to || sender;

	var newChatEl = createNewChat(sender, message);

	if (chatTo == to) {
		messageBoardEl.appendChild(newChatEl);
	} else {
		var toEl = usernameListEl.querySelector('#' + to);
		addCountMessage(toEl);
	}

	if (chatRoom[to])
		chatRoom[to].push(newChatEl);
	else
		chatRoom[to] = [ newChatEl ];
}

function removeUser(removedUsername) {

	usernameListEl.removeChild(usernameListEl.querySelector('#' + removedUsername));

	if (chatTo == removedUsername)
		chatTo = '';
}

function createNewChat(sender, message) {
	var newChatDivEl = document.createElement('div');

	var messageEl = document.createElement('span');

	if (sender == usernameInputEl.value) {

		newChatDivEl.classList.add("text-primary");
		newChatDivEl.classList.add("text-right");
	} else {
		newChatDivEl.classList.add("text-secondary");
		newChatDivEl.classList.add("text-left");
	}

	messageEl.textContent = message;

	newChatDivEl.appendChild(messageEl);
	return newChatDivEl;
}

function addCountMessage(toEl) {
	var countEl = toEl.querySelector('.count');
	if (countEl) {
		var count = countEl.textContent;
		count = +count;
		countEl.textContent = count + 1;
	} else {
		var countEl = document.createElement('span');
		countEl.classList.add('count');
		countEl.classList.add('badge');
		countEl.classList.add('badge-danger');
		countEl.textContent = '1';
		toEl.appendChild(countEl);
	}
}

sendBtnEl.onclick = sendMessage;

function sendMessage() {
	var message = messageInputEl.value;
	if (message == '' || chatTo == '')
		return;
	socket.send(chatTo + '|' + message);
	messageInputEl.value = '';

	var sender = usernameInputEl.value;
	getMessage(sender, message, chatTo);
	messageBoardEl.scrollTop = messageBoardEl.scrollHeight;
}

function chatToFn(username, fullname) {
	return function(e) {
		if (username == usernameInputEl.value)
			return;

		var countEl = usernameListEl.querySelector('#' + username + '>.count');
		if (countEl) {
			countEl.remove();
		}

		chatToEl.textContent = " " + username;
		chatTo = username;
		messageBoardEl.innerHTML = '';

		var conversationList = chatRoom[chatTo];

		console.log(conversationList);

		if (!conversationList)
			return;
		var df = document.createDocumentFragment();
		conversationList.forEach(function(conversation) {

			console.log(conversation);
			df.appendChild(conversation);
		});
		messageBoardEl.appendChild(df);
	}
}
