package bean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.Serializable;
import java.util.Date;
import java.util.Properties;

/**
 * Created by Christian on 16-05-2015.
 */
@SessionScoped
@ManagedBean(name = "correo")
public class Correo implements Serializable {

    private static final long serialVersionUID = 1L;
    private String correoDe;
    private String correoPara;
    private String correoTitulo;
    private String correoContenido;
    private String imagenEnviar;
    private String mensaje;
    private String passCorreo;
    private String host;
    private String puerto;
    private boolean visible;


    @PostConstruct
    public void init() {
        visible = false;
        imagenEnviar = "/resources/img/email_accept.ico";
    }

    public void enviarCorreo() {

        host = validarCorreo();
        Transport transport = null;
        mensaje = "Enviando correo";

        Properties properties = new Properties();
        properties.setProperty("mail.smtp.host", host);  //smtp.gmail.com
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.smtp.port", puerto);   //567 gmail // 587 hotmail
        properties.setProperty("mail.smtp.user", correoDe);
        properties.setProperty("mail.smtp.auth", "true");
        Session session = Session.getDefaultInstance(properties);
        //session.setDebug(true);

        try {
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(correoDe));
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(correoPara));
            mimeMessage.setSubject(correoTitulo);
            mimeMessage.setSentDate(new Date());
            mimeMessage.setText(correoContenido);
            transport = session.getTransport("smtp");
            transport.connect(correoDe, passCorreo);
            transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
            transport.close();
            mensaje ="El correo se ha enviado exitosamente";
        } catch (MessagingException e) {
            //e.printStackTrace();
           mensaje = "Ocurrió un error y no se envió el correo";
        }
    }

    public String validarCorreo() {
        String[] correo = correoDe.split("@");
        String[] dominioCorreo = correo[1].split("\\.");
        if (dominioCorreo[0].equals("hotmail")){
            puerto = "587";
          return  "smtp.live.com";
        } else if(dominioCorreo[0].equals("gmail")){
            puerto = "567";
            return "smtp.gmail.com";
        }else{
            return "";
        }
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public String getCorreoDe() {
        return correoDe;
    }

    public void setCorreoDe(String correoDe) {
        this.correoDe = correoDe;
    }

    public String getCorreoPara() {
        return correoPara;
    }

    public void setCorreoPara(String correoPara) {
        this.correoPara = correoPara;
    }

    public String getCorreoTitulo() {
        return correoTitulo;
    }

    public void setCorreoTitulo(String correoTitulo) {
        this.correoTitulo = correoTitulo;
    }

    public String getCorreoContenido() {
        return correoContenido;
    }

    public void setCorreoContenido(String correoContenido) {
        this.correoContenido = correoContenido;
    }

    public String getImagenEnviar() {
        return imagenEnviar;
    }

    public void setImagenEnviar(String imagenEnviar) {
        this.imagenEnviar = imagenEnviar;
    }

    public String getPassCorreo() {
        return passCorreo;
    }

    public void setPassCorreo(String passCorreo) {
        this.passCorreo = passCorreo;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPuerto() {
        return puerto;
    }

    public void setPuerto(String puerto) {
        this.puerto = puerto;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

}
