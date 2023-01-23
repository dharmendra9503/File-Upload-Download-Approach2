# File-Upload-Download-Approach2
### This is a small file upload and download module developed in Spring Boot.

In spring Boot two approach present for upload and download file/image.

Approach 1. Directly store file/image in database as a bit array.
Approach 2. Store file/image in Directory and store path of that file/image in data base.

In this module we use Approach 2 to upload and download file/image.

> This module is only image(.png, .jpeg, .jpg) specific.

> Image size limit : 15MB

### Endpoint for upload image: (use POST request)
 > http://loalhost:4042/file/upload
 
### Endpoint for download image: (use GET request)
 > http://loalhost:4042/file/download/{image-name}
