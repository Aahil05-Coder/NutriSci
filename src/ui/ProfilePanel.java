package ui;

import controller.ProfileController;
import model.UserProfile;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.function.Consumer;

public class ProfilePanel extends JPanel {
    private JComboBox<UserProfile> userCombo;
    private JTextField nameField = new JTextField(15);
    private JComboBox<String> genderBox = new JComboBox<>(new String[]{"Male","Female"});
    private JTextField dobField = new JTextField(10);
    private JSpinner heightSpinner = new JSpinner(new SpinnerNumberModel(170,50,250,1));
    private JSpinner weightSpinner = new JSpinner(new SpinnerNumberModel(70,30,300,1));
    private JButton createButton = new JButton("Create User");
    private JButton saveButton = new JButton("Save User");
    private JButton selectButton = new JButton("Select User");
    private Consumer<UserProfile> userSelectedListener;

    public ProfilePanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4,4,4,4);
        c.anchor = GridBagConstraints.WEST;

        userCombo = new JComboBox<>();
        c.gridx=0; c.gridy=0; add(new JLabel("Select User:"), c);
        c.gridx=1; add(userCombo, c);

        c.gridy++; c.gridx=0; add(new JLabel("Name:"), c);
        c.gridx=1; add(nameField, c);

        c.gridy++; c.gridx=0; add(new JLabel("Gender:"), c);
        c.gridx=1; add(genderBox, c);

        c.gridy++; c.gridx=0; add(new JLabel("DOB (YYYY-MM-DD):"), c);
        c.gridx=1; add(dobField, c);
        dobField.setText(LocalDate.now().minusYears(25).toString());

        c.gridy++; c.gridx=0; add(new JLabel("Height (cm):"), c);
        c.gridx=1; add(heightSpinner, c);

        c.gridy++; c.gridx=0; add(new JLabel("Weight (kg):"), c);
        c.gridx=1; add(weightSpinner, c);

        c.gridy++; c.gridx=0; c.gridwidth=2;
        JPanel btnPanel = new JPanel();
        btnPanel.add(createButton);
        btnPanel.add(saveButton);
        btnPanel.add(selectButton);
        add(btnPanel, c);

        // Connect controller (auto loads users, handles create/save)
        new ProfileController(this);

        selectButton.addActionListener(e -> {
            UserProfile selected = (UserProfile) userCombo.getSelectedItem();
            if (selected != null && selected.getId() > 0 && userSelectedListener != null) {
                userSelectedListener.accept(selected);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a valid user!");
            }
        });
    }

    // --- Used by controller ---
    public JComboBox<UserProfile> getUserCombo() { return userCombo; }
    public JButton getCreateButton() { return createButton; }
    public JButton getSaveButton() { return saveButton; }

    public void populate(UserProfile p) {
        nameField.setText(p.getName());
        genderBox.setSelectedItem(p.getGender());
        dobField.setText(p.getDob() != null ? p.getDob().toString() : "");
        heightSpinner.setValue(p.getHeightCm());
        weightSpinner.setValue(p.getWeightKg());
    }

    public UserProfile collectInput() {
        return new UserProfile(
            nameField.getText(),
            (String)genderBox.getSelectedItem(),
            LocalDate.parse(dobField.getText()),
            (Integer)heightSpinner.getValue(),
            (Integer)weightSpinner.getValue()
        );
    }

    public void clearFields() {
        nameField.setText("");
        genderBox.setSelectedIndex(0);
        dobField.setText(LocalDate.now().minusYears(25).toString());
        heightSpinner.setValue(170);
        weightSpinner.setValue(70);
    }

    // Allow Main to set a listener to swap panels
    public void setUserSelectedListener(Consumer<UserProfile> listener) {
        this.userSelectedListener = listener;
    }
}
