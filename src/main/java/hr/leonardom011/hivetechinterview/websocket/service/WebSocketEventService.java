package hr.leonardom011.hivetechinterview.websocket.service;

import hr.leonardom011.hivetechinterview.websocket.model.response.TaskChangedResponse;

public interface WebSocketEventService {


    void notifyTaskChanged(TaskChangedResponse taskChangedResponse);
}
