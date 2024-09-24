package Service;

import java.util.List;

import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    MessageDAO messageDAO;
    AccountService accountService;

    public MessageService() {
        messageDAO = new MessageDAO();
        accountService = new AccountService();
    }

    /**
     * 
     * @param message
     * @return
     */
    public Message addMessage(Message message) {
        if (valid(message)) {
            return messageDAO.insertMessage(message);
        } else {
            return null;
        }
    }

    /**
     * 
     * @return
     */
    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    /**
     * 
     * @param pathParamId
     * @return
     */
    public Message getMessageById(String pathParamId) {
        int id = Integer.parseInt(pathParamId);
        return messageDAO.getMessageById(id);
    }


    /**
     * 
     * @param pathParamId
     * @return
     */
    public Message deleteMessage(String pathParamId) {
        int id = Integer.parseInt(pathParamId);
        return messageDAO.deleteMessage(id);
    }

    /**
     * 
     * @param message
     * @param pathParamId
     * @return
     */
    public Message patchMessage(Message message, String pathParamId) {
        int id = Integer.parseInt(pathParamId);
        if (valid(message)){
            return messageDAO.updateMessage(message,id);
        } else {
            return null;
        }
    }

    /**
     * 
     * @param pathParamId
     * @return
     */
    public List<Message> getMessageByAccountId(String pathParamId) {
        int id = Integer.parseInt(pathParamId);
        return messageDAO.getMessageByAccountId(id);
    }

    /**
     * 
     * @param message
     * @return
     */
    private Boolean valid(Message message) {
        // - Account Exists
        // - Length is between 1 - 255
        return validateAccount(message.posted_by) 
            && (message.message_text.length() <= 255)
            && (message.message_text.length() > 0);
    }

    /**
     * 
     * @param id
     * @return
     */
    private Boolean validateAccount(int id){
        return accountService.isAccount(id);
    }
    
}
