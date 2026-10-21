-- 1. Aggiunge la colonna, per ora FACOLTATIVA
ALTER TABLE job_applications ADD COLUMN user_id BIGINT;

-- 2. Assegna le candidature già esistenti al primo utente registrato
UPDATE job_applications
SET user_id = (SELECT MIN(id) FROM users)
WHERE user_id IS NULL;

-- 3. Se non esiste nessun utente, le candidature rimaste senza proprietario vengono eliminate
DELETE FROM job_applications WHERE user_id IS NULL;

-- 4. Ora che tutte le righe hanno un valore, la colonna può diventare OBBLIGATORIA
ALTER TABLE job_applications ALTER COLUMN user_id SET NOT NULL;

-- 5. Vincolo di foreign key: user_id deve esistere in users
ALTER TABLE job_applications
    ADD CONSTRAINT fk_job_applications_user
        FOREIGN KEY (user_id) REFERENCES users (id)
            ON DELETE CASCADE;

-- 6. Indice: ogni richiesta filtrerà per utente
CREATE INDEX idx_job_applications_user_id ON job_applications (user_id);