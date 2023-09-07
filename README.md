# NglClone Backend
This is the backend part of the ngl.link clone I've developed using Springboot and Angular. Since they're a startup and actually have multiple employees I thought about just how hard it'd be write it from scratch. You can see the frontend part from [here](https://github.com/egeulk/ngl-clone-frontend).

# Running on local
Install postgresql and run the table creation script in the 'tables.sql' file. If you want to expand functionality such as showing notifications whenever a new question is entered you can put a firebase config inside the 'resources/firebase-config' folder, but it's not yet implemented in frontend.
You can change the default password by editing the UserDetailsServiceBean.createUser() method.

# Running on Heroku
After creating an app, you should add the postgresql as an extension. And then with a program of your choice (I used pgadmin4) connect to the database using the link, username and password given by Heroku. After connecting, run the 'tables.sql' script.
Deploy the backend with [Github](https://devcenter.heroku.com/articles/github-integration) or using [Heroku CLI](https://devcenter.heroku.com/articles/git)
The Heroku will manage SSL certificates itself without any configuration, simply activate the SSL from the apps' settings.

Inside the Procfile, you'll find the important deployment options. The defaults will work, but you can configure them to your heart's content.

After deploying the backend, you can configure the CORS endpoints of two controller classes. By default, every domain is allowed access, but you can restrict it to only allow the frontend to access the backend.
