package controller;

import model.UserProfile;
import service.ProfileService;
import ui.ProfilePanel;

import javax.swing.*;
import java.util.List;

public class ProfileController {
    private final ProfilePanel panel;
    private final ProfileService service = new ProfileService();

    public ProfileController(ProfilePanel panel) {
        this.panel = panel;
        initUserCombo();
        wireActions();
    }

    private void initUserCombo() {
        List<UserProfile> users = service.getAllUsers();
        panel.getUserCombo().removeAllItems();
        panel.getUserCombo().addItem(new UserProfile(-1, "<New User>", "Male", null, 170, 70));
        for (UserProfile u : users)
            panel.getUserCombo().addItem(u);
        panel.getUserCombo().setSelectedIndex(0);
    }

    private void wireActions() {
        panel.getUserCombo().addActionListener(e -> {
            UserProfile selected = (UserProfile) panel.getUserCombo().getSelectedItem();
            if (selected != null && selected.getId() > 0) {
                panel.populate(selected);
            } else {
                panel.clearFields();
            }
        });

        panel.getCreateButton().addActionListener(e -> {
            panel.getUserCombo().setSelectedIndex(0);
            panel.clearFields();
        });

        panel.getSaveButton().addActionListener(e -> {
            UserProfile selected = (UserProfile) panel.getUserCombo().getSelectedItem();
            UserProfile input = panel.collectInput();
            try {
                if (selected != null && selected.getId() > 0) {
                    UserProfile updated = new UserProfile(selected.getId(), input.getName(), input.getGender(), input.getDob(), input.getHeightCm(), input.getWeightKg());
                    service.saveOrUpdate(updated);
                    JOptionPane.showMessageDialog(panel, "Profile updated!");
                } else {
                    service.saveOrUpdate(input);
                    JOptionPane.showMessageDialog(panel, "Profile created!");
                }
                initUserCombo();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, "Error: " + ex.getMessage());
            }
        });

        // The selectButton action should be handled by the ProfileWindow to open the rest of the app!
    }
}
