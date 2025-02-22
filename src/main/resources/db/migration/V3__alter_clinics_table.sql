ALTER TABLE clinics ADD COLUMN user_id UUID NOT NULL REFERENCES user_clinic(id);
