<!DOCTYPE html>
<html lang="en" ng-app="crudApp">
    <head>
        <title>${title}</title>
        <link href="css/bootstrap.css" rel="stylesheet">
        <link href="css/app.css" rel="stylesheet">
    </head>

    <body>
        <div ui-view>
            <script src="js/lib/angular.min.js"/>
            <script src="js/lib/angular-ui-router.min.js"/>
            <script src="js/lib/localforage.min.js"/>
            <script src="js/lib/ngStorage.min.js"/>
            <script src="js/app/app.js"/>
            <script src="js/app/UserService.js"/>
            <script src="js/app/UserController.js"/>
        </div>
    </body>
</html>