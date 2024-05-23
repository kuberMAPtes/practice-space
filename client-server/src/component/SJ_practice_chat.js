// npm install react-use-websocket;

import React from 'react';
import {useEffect, useState, useCallback} from 'react';
import useWebSocket from 'react-use-websocket';
import {ReadyState} from 'react-use-websocket';

const SJ_practice_chat = () => {
    
    //Public API that will echo messages sent to it back to the client
    const [socketUrl, setSocketUrl] = useState('wss://echo.websocket.org');
    
    //const [messageHistory, setMessageHistory] = useState<MessageEvent<any>[]>([]);
    const [messageHistory, setMessageHistory] = useState([]);

    const { sendMessage, lastMessage, readyState } = useWebSocket(socketUrl);
  
    useEffect(() => {
      if (lastMessage !== null) {
        setMessageHistory((prev) => prev.concat(lastMessage));
      }
    }, [lastMessage]);
  
    const handleClickChangeSocketUrl = useCallback(
      () => setSocketUrl('wss://demos.kaazing.com/echo'),
      []
    );
  
    const handleClickSendMessage = useCallback(() => sendMessage('Hello'), []);
  
    const connectionStatus = {
      [ReadyState.CONNECTING]: 'Connecting',
      [ReadyState.OPEN]: 'Open',
      [ReadyState.CLOSING]: 'Closing',
      [ReadyState.CLOSED]: 'Closed',
      [ReadyState.UNINSTANTIATED]: 'Uninstantiated',
    }[readyState];
  
    return (
      <div>
        <button onClick={handleClickChangeSocketUrl}>
          Click Me to change Socket Url
        </button>

        <button
          onClick={handleClickSendMessage}
          disabled={readyState !== ReadyState.OPEN}
        >
          Click Me to send 'Hello'
        </button>

        <br/><br/>

        <span>The WebSocket is currently 😊{connectionStatus} (요거 connection Status) </span> 

        <br/>

        {lastMessage ? <span>Last message: 😒{lastMessage.data}</span> : null} (요거 lastMessage.data)

        <br/>

        <ul>
          {messageHistory.map((message, idx) => (
            <span key={idx}>{message ? message.data : null} </span>
          ))}
        </ul>
      </div>
    );
};

export default SJ_practice_chat;