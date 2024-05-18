        //npm init -y
        //npm install nodemon (안하면 node server.js 로 실행)
        //npm install express 
        //npm install socket.io

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

        io.on('connection', function (socket) {

            console.log(socket.id, ' connected...');

            // receive a nickname changed
            var nickname = 'NEWBIE';

            socket.on("nickname", function (data) {
                console.log("nickname : ", data)

                if(!nickname){
                    nickname = data;
                    socket.emit('msg', `${socket.id} has changed nickname as ${nickname}.`)
                } else {
                    nickname_past = nickname;
                    nickname = data;

                    socket.emit('msg', `${nickname_past} has changed nickname as ${nickname}`)
                }
            });

            // broadcasting a entering message to everyone who is in the chatroom
            io.emit('msg', `NEWBIE (${socket.id}) has entered the chatroom. `);
        
            // message receives
            socket.on('msg', function (data) {
                console.log(socket.id,': ', data);
                // broadcasting a message to everyone except for the sender
                socket.broadcast.emit('msg', `${socket.id}: ${data}`);
                
                // emit a message to sender himself also    
                socket.emit('msg', `${nickname} (${socket.id}): ${data}` );
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