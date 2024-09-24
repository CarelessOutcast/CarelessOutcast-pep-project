package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Service.AccountService;
import Model.Account;

import Service.MessageService;
import Model.Message;

import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {

    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(){
        accountService = new AccountService();
        messageService = new MessageService();
    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();

        // TODO: Add Documentation for endpoints
        app.post("/register", this::postAccountRegistrationHandler);
        app.post("/login", this::postAccountLoginHandler);
        app.post("/messages", this::postMessagesHandler);
        app.get("/messages", this::getMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageHandler);
        app.patch("/messages/{message_id}", this::patchMessageHandler);
        app.get("/accounts/{account_id}/messages", this::getMessagesByAccountIdHandler);

        return app;
    }

    /*
     * TODO: Add AccountRegistration Handler description
    */
    private void postAccountRegistrationHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.addAccount(account);
        if (addedAccount == null) {
            ctx.status(400);
        } else {
            ctx.json(mapper.writeValueAsString(addedAccount));
            ctx.status(200);
        }
    }
    
    /*
     * TODO: Add postAccountLoginHandler description
    */
    private void postAccountLoginHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account theAccount = accountService.getAccount(account);
        if (theAccount == null) {
            ctx.status(401);
        } else {
            ctx.json(mapper.writeValueAsString(theAccount));
        }
    }

    /*
     * TODO: Add postMessagesHandler description
    */
    private void postMessagesHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message addedMessage= messageService.addMessage(message);
        if (addedMessage== null) {
            ctx.status(400);
        } else {
            ctx.json(mapper.writeValueAsString(addedMessage));
        }
    }

    /*
     * TODO: Add getMessagesHandler description
    */
    private void getMessagesHandler(Context ctx) {
        ctx.json(messageService.getAllMessages());
    }

    /*
     * TODO: Add getMessageByIdHandler description
    */
    private void getMessageByIdHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message gotMessage = messageService.getMessageById(ctx.pathParam("message_id"));
        if (gotMessage != null) {
            ctx.json(mapper.writeValueAsString(gotMessage));
        } else {
            ctx.status(200).result("");
        }

    }

    /*
     * TODO: Add deleteMessageHandler description
    */
    private void deleteMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message gotMessage = messageService.deleteMessage(ctx.pathParam("message_id"));
        if (gotMessage != null) {
            ctx.json(mapper.writeValueAsString(gotMessage));
        } else {
            ctx.status(200).result("");
        }
    }
    
    /*
     * TODO: Add patchMessageHandler description
    */
    private void patchMessageHandler(Context ctx) throws JsonProcessingException{
        Message existingMessage = messageService.getMessageById(ctx.pathParam("message_id"));
        if (existingMessage == null) {
            ctx.status(400);
            return;
        }
        ObjectMapper mapper = new ObjectMapper();
        Message updatedMessage = mapper.readValue(ctx.body(), Message.class);
        if (updatedMessage.getMessage_text() != null) {
            existingMessage.setMessage_text(updatedMessage.getMessage_text());
        }
        if (updatedMessage.getPosted_by() != 0) {
            existingMessage.setPosted_by(updatedMessage.getPosted_by());
        }
        if (updatedMessage.getTime_posted_epoch() != 0) {
            existingMessage.setTime_posted_epoch(updatedMessage.getTime_posted_epoch());
        }
        Message patchedMessage = messageService.patchMessage(existingMessage, ctx.pathParam("message_id"));
        if (patchedMessage == null) {
            ctx.status(400);
        } else {
            ctx.json(mapper.writeValueAsString(patchedMessage));
        }
    }

    /*
     * TODO: Add getAccountMessagesHandler description
    */
    private void getMessagesByAccountIdHandler(Context ctx) {
        ctx.json(messageService.getMessageByAccountId(ctx.pathParam("account_id")));
    }

}