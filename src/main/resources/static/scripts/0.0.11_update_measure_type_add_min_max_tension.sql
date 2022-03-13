ALTER TABLE measure_type
    ADD min_diastolic FLOAT NULL,
    ADD max_diastolic FLOAT NULL,
    ADD min_systolic FLOAT NULL,
    ADD max_systolic FLOAT NULL,
    DROP COLUMN diastolic,
    DROP COLUMN systolic;

UPDATE measure_type SET min_diastolic = 70 WHERE code='tension';
UPDATE measure_type SET max_diastolic = 90 WHERE code='tension';
UPDATE measure_type SET min_systolic = 100 WHERE code='tension';
UPDATE measure_type SET max_systolic = 140 WHERE code='tension';