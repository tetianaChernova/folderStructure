INSERT INTO folder_structure.folder2folders (folder_id, folder_name, father_folder_id)
VALUES (1, '$ROOT$', null);
INSERT INTO folder_structure.folder2folders (folder_id, folder_name, father_folder_id)
VALUES (2, 'Folder_1', 1);
INSERT INTO folder_structure.folder2folders (folder_id, folder_name, father_folder_id)
VALUES (3, 'Folder_2', 1);
INSERT INTO folder_structure.folder2folders (folder_id, folder_name, father_folder_id)
VALUES (4, 'SubFolder_1', 2);
INSERT INTO folder_structure.folder2folders (folder_id, folder_name, father_folder_id)
VALUES (5, 'SubFolder_2', 2);
INSERT INTO folder_structure.folder2folders (folder_id, folder_name, father_folder_id)
VALUES (6, 'SubFolder_3', 2);

INSERT INTO folder_structure.files (file_id, folder_id, file_name, mime_type, file_size)
VALUES (1, 1, 'image1.gif', 'image/jpeg', 13406);
INSERT INTO folder_structure.files (file_id, folder_id, file_name, mime_type, file_size)
VALUES (2, 1, 'image2.gif', 'image/gif', 3410);
INSERT INTO folder_structure.files (file_id, folder_id, file_name, mime_type, file_size)
VALUES (3, 2, 'image3.jpg', 'image/jpeg', 33756);
INSERT INTO folder_structure.files (file_id, folder_id, file_name, mime_type, file_size)
VALUES (4, 2, 'image4.png', 'image/png', 10024);
INSERT INTO folder_structure.files (file_id, folder_id, file_name, mime_type, file_size)
VALUES (5, 4, 'readme1.txt', 'text/plain', 511);
INSERT INTO folder_structure.files (file_id, folder_id, file_name, mime_type, file_size)
VALUES (6, 3, 'document1.doc', 'application/msword', 27301);

