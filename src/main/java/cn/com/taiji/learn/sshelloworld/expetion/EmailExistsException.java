package cn.com.taiji.learn.sshelloworld.expetion;

public class EmailExistsException extends Throwable {

    public EmailExistsException(final String message) {
        super(message);
    }

}
