package dcberr.taskz.modules.message.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dcberr.taskz.modules.message.dto.MessageResponse;
import dcberr.taskz.modules.message.dto.MockMessageRequest;
import dcberr.taskz.modules.message.provider.MockMessageProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mock/messages")
public class MockMessageController {

    private final MockMessageProvider mockMessageProvider;

    @PostMapping
    public MessageResponse receiveMessage(
            @Valid
            @RequestBody
            MockMessageRequest request
    ) {

        return mockMessageProvider.receiveMessage(
                request
        );
    }
}
