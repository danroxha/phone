package phone.window;

import phone.component.ImageLabel;
import phone.entity.Person;
import phone.repository.LocalStorage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ContactDetailsWindow extends JFrame {
  public static void run() {
    new RegisterContactWindow();
  }

  public static void run(Runnable runnable) {
    new ContactDetailsWindow(runnable);
  }

  public static void run(Person person, Runnable runnable) {
    new ContactDetailsWindow(person, runnable);
  }

  private Runnable runnable;
  private Person person;

  public ContactDetailsWindow() {
    setComponents();
    setDefaultBehavior();
  }

  public ContactDetailsWindow(Runnable runnable) {
    this();
    this.runnable = runnable;
  }

  public ContactDetailsWindow(Person person, Runnable runnable) {
    this.runnable = runnable;
    this.person = person;
    setComponents();
    setDefaultBehavior();
  }

  private  void setComponents() {
    buildFormContact();
  }

  private void setDefaultBehavior() {
    setTitle("Phone - Registrar contato");
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    setSize(380, 500);
    setResizable(Boolean.FALSE);
    setLocationRelativeTo(null);
    setVisible(Boolean.TRUE);
  }

  public void buildFormContact() {
    var gbag = new GridBagLayout();
    var gcons = new GridBagConstraints();

    var iconContactPanel = new JPanel();
    var formPanel = new JPanel();

    iconContactPanel.add(new ImageLabel("resources/icons/cellphone-128.png"));
    iconContactPanel.add(new ImageLabel("resources/icons/plus-32.png"));

    formPanel.setFont(new Font("Serif", Font.PLAIN, 16));
    formPanel.setLayout(gbag);

    gcons.fill = GridBagConstraints.BOTH;
    gcons.weightx = 1.0; //peso

    var inputName = makeTextInputField("Nome", gbag, gcons, formPanel);
    inputName.setText(person.getName());
    inputName.addKeyListener(new KeyListener() {
      @Override
      public void keyTyped(KeyEvent e) {}
      @Override
      public void keyPressed(KeyEvent e) {}
      @Override
      public void keyReleased(KeyEvent e) {
        person.setName(inputName.getText());
      }
    });


    gcons.gridwidth = GridBagConstraints.REMAINDER;

    var inputPhone = makeTextInputField("Telefone", gbag, gcons, formPanel);
    inputPhone.setText(person.getPhoneNumber());
    inputPhone.addKeyListener(new KeyListener() {
      @Override
      public void keyTyped(KeyEvent e) {}
      @Override
      public void keyPressed(KeyEvent e) {}
      @Override
      public void keyReleased(KeyEvent e) {
        person.setPhoneNumber(inputName.getText());
      }
    });


    gcons.weightx = 0.0;
    var inputEmail = makeTextInputField("E-mail", gbag, gcons, formPanel);
    inputEmail.setText(person.getEmail());
    inputEmail.addKeyListener(new KeyListener() {
      @Override
      public void keyTyped(KeyEvent e) {
      }
      @Override
      public void keyPressed(KeyEvent e) {}
      @Override
      public void keyReleased(KeyEvent e) {
        person.setEmail(inputEmail.getText());
      }
    });

    var inputCompany = makeTextInputField("Empresa", gbag, gcons, formPanel);
    inputCompany.setText(person.getCompany());
    inputCompany.addKeyListener(new KeyListener() {
      @Override
      public void keyTyped(KeyEvent e) {
      }
      @Override
      public void keyPressed(KeyEvent e) {}
      @Override
      public void keyReleased(KeyEvent e) {
        person.setCompany(inputCompany.getText());
      }
    });

    var inputTitle = makeTextInputField("Titulo", gbag, gcons, formPanel);
    inputTitle.setText(person.getTitle());
    inputTitle.addKeyListener(new KeyListener() {
      @Override
      public void keyTyped(KeyEvent e) {
      }
      @Override
      public void keyPressed(KeyEvent e) {}
      @Override
      public void keyReleased(KeyEvent e) {
        person.setTitle(inputTitle.getText());
      }
    });

    var registerButton = new JButton("Salvar contato", new ImageIcon("resources/icons/save-16.png"));
    var removeButton = new JButton("Excluir contato", new ImageIcon("resources/icons/save-16.png"));

    registerButton.addActionListener(e ->  {
      LocalStorage.contacts.removeIf(p -> p.equals(person));
      LocalStorage.contacts.add(person);

      if(runnable != null)
        runnable.run();
      dispose();
    });

    removeButton.addActionListener(e -> {
      LocalStorage.contacts.removeIf(p -> p.equals(person));

      if(runnable != null)
        runnable.run();
      dispose();
    });

    gcons.fill = GridBagConstraints.BOTH;
    gcons.weightx = 1.0; //peso
    gbag.setConstraints(registerButton, gcons);

    gcons.weightx = 2.0; //peso
    gbag.setConstraints(removeButton, gcons);

    formPanel.add(registerButton);
    formPanel.add(removeButton);

    setLayout(new BorderLayout());

    add(BorderLayout.NORTH, iconContactPanel);
    add(BorderLayout.CENTER, formPanel);

  }
  private JTextField makeTextInputField(String label, GridBagLayout gb, GridBagConstraints gc) {
    return makeTextInputField(label, gb, gc, null);
  }

  private JTextField makeTextInputField(String label, GridBagLayout gb, GridBagConstraints gc, JPanel panel) {

    var container = new JPanel();

    var lblTextField = new JLabel(label);
    var inputTextField = new JTextField();

    inputTextField.setFont(new Font("Serif", Font.PLAIN, 18));

    container.setLayout(new GridLayout(2, 1));

    container.add(lblTextField);
    container.add(inputTextField);

    gb.setConstraints(container, gc);

    if(panel != null)
      panel.add(container);
    else
      getContentPane().add(container);

    return inputTextField;
  }
}
