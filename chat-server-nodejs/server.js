//npm init -y
//npm install nodemon (안하면 node server.js 로 실행)
//npm install express 
//npm install socket.io

// 몽고 DB 접속
var mongoose = require('mongoose');
const mongooseFunctionSJ = require('./mongoDB_test');

var modelChat;
async function initialSetting(){
    modelChat = await mongooseFunctionSJ.mongooseSetup();
}

initialSetting();

// *** 복붙하기 위한 임시 코드 ***
async function temp(){

await mongooseFunctionSJ.mongooseWrite(modelChat, chat);
await mongooseFunctionSJ.mongooseRead(modelChat, chat);
await mongooseFunctionSJ.mongooseReadAll(modelChat);
await mongooseFunctionSJ.mongooseUpdate(modelChat, chat);
await mongooseFunctionSJ.mongooseDelete(modelChat, chat);

mongoose.connection.close();
}

// 웹서버 개설
const express = require('express');
const app = express();

const port = 8087;
const server = app.listen(port, function() {
    console.log('Listening on '+port);
});

// socketIO 개설(?)
const SocketIO = require('socket.io');
const io = SocketIO(server, {path: '/socket.io'});

io.on('connection', async function (socket) {

    console.log(socket.id, ' connected...');

    // show entire chat room member and number
    const roomInfo = {
        size: socket.adapter.rooms.size, 
        rooms: Array.from(socket.adapter.rooms)
    }
    console.log(roomInfo);
    socket.emit('room_info', roomInfo);

    // receive a nickname changed
    var nickname = 'NEWBIE';

    socket.on("nickname", function (data) {
        console.log("nickname : ", data)

        if(!nickname){
            nickname = data;
            
            socket.emit('msg', `${socket.id} has changed nickname as ${nickname}.`)
            socket.broadcast.emit('msg', `${socket.id} has changed nickname as ${nickname}.`)
        } else {
            nickname_past = nickname;
            nickname = data;

            socket.emit('msg', `${nickname_past} has changed nickname as ${nickname}.`)
            socket.broadcast.emit('msg', `${nickname_past} has changed nickname as ${nickname}`)
        }
    });

    // broadcasting a entering message to everyone who is in the chatroom
    io.emit('msg', `NEWBIE (${socket.id}) has entered the chatroom. (입장시간 : ${socket.handshake.time}))`)

    
    // message receives
    socket.on('msg', async function (chatMsg) {
        console.log(socket.id,': ', chatMsg);
        // broadcasting a message to everyone except for the sender
        const chat = {
            nickname : nickname,
            'socket.id' : socket.id,
            chatMsg : chatMsg,
            time : new Date().toString()
        }

        socket.broadcast.emit('chat', chat);
        
        // emit a message to sender himself also    
        socket.emit('chat', chat );

        // store a message to DB
        await mongooseFunctionSJ.mongooseWrite(modelChat, chat);
    });

    // user connection lost
    socket.on('disconnect', function (data) {
        io.emit('msg', `${socket.id} has left the chatroom.`);
    });
});

// /chat 으로 들어올 경우 client-server-nodejs 에서 html 뿌려줌
app.get('/chat', function(req, res) {
    res.sendFile(__dirname + '/chat.html');
});

// /react 으로 들어올 경우 client-server에서 리액트로만든 html 뿌려줌
var path = require('path');

app.get('/react', function(req, res) {
    res.sendFile(path.resolve('../client-server/build/index.html'));
});

// 채팅내용 조회
app.get('/api/chats', async (req, res) => {
    try {
        const chatHistory = await mongooseFunctionSJ.mongooseReadAll(modelChat);
        console.log("채팅보낼게요", chatHistory);
        res.json(chatHistory);
    } catch (error) {
        res.status(500).json({ error: 'Error fetching chat history' });
    }
});