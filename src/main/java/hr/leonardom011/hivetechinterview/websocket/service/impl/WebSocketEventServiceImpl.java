package hr.leonardom011.hivetechinterview.websocket.service.impl;

import hr.leonardom011.hivetechinterview.websocket.model.response.TaskChangedResponse;
import hr.leonardom011.hivetechinterview.websocket.service.WebSocketEventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import static hr.leonardom011.hivetechinterview.constant.WebSocketConstant.TASK_CHANGED_TOPIC_PATH;

@Service
@Slf4j
public class WebSocketEventServiceImpl implements WebSocketEventService {

    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketEventServiceImpl(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void notifyTaskChanged(TaskChangedResponse taskChangedResponse) {
        log.info("Task changed: {}", taskChangedResponse);
        messagingTemplate.convertAndSend(TASK_CHANGED_TOPIC_PATH, taskChangedResponse);
    }

}
