package Service;

import Entity.Etextbook;
import Repository.EtextbookRepository;

public class EtextbookService {

    private final EtextbookRepository etextbookRepository;

    // Constructor
    public EtextbookService() {
        this.etextbookRepository = new EtextbookRepository();
    }

    public void createEtextbook(int textbookId, String title) {
        // Create an Etextbook object using the provided id and title
        Etextbook newEtextbook = new Etextbook(textbookId, title);
        
        // Use the repository to save the new e-textbook
        try {
            etextbookRepository.createEtextbook(newEtextbook);
        } catch (Exception e) {
            System.err.println("Error creating e-textbook: " + e.getMessage());
        }
    }
}
