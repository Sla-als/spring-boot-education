# spring-boot-education

http://localhost:8189/education/ - адрес приложения

**Lesson view page .html**
Таблица с информацией об уроках
Files List [ARRAY ID FILES] - массив id прикрепленных к уроку файлов

**Lesson edit page .html**
Формы для создания нового урока или обновления информации о существующем

**File view page .html**
Таблица с информацией о файлах, с возможностью скачать файл

**File edit page .html**
Формы для создания нового вложения к уроку
с формами:  
к какому уроку прикреплен файл (отображается в Files List [ARRAY ID FILES]  ), 
 указания пути  - Path  "upload-dir" - путь по умолчанию, если отправить в форму String - создается новая директория (только двухуровневая организация директорий (upload-dir/lesson01/file.file)) 
 формы загрузки файлов

_База данных Postgresql
Сборщик Maven_
