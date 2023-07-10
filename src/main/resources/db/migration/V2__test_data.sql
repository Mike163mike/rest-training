INSERT INTO task (id, details, completed)
VALUES (gen_random_uuid(),
        'First message',
        false),
       (gen_random_uuid(),
        'Second message',
        false),
       (gen_random_uuid(),
        'Third message',
        false);