![sweetControl](/src/main/resources/static/img/README/sweetControl_BG.png "Sweet Control")
#

[Overview](#overview-and-features)
:small_blue_diamond:
[Tech Stack](#tech-stack)
:small_blue_diamond:
[Update History](#update-history)
:small_blue_diamond:
[Roadmap](#roadmap)
:small_blue_diamond:
[License](#license)
:small_blue_diamond:
[Authors](#authors-and-acknowledgment)

> Sweet Control is a web-based system designed to help with edible products inventory management.

* Register different Ingredients types and their respective Inventories informations, including total and left amounts, price and expiration date.

* Register your own Recipes, with informations about Ingredients and their amounts.

* Keep track of how many times You can still cook Recipes and how much the next cooking will cost.

* Follow up your Cooked Recipes with Inventories and amounts were used, making date and cost.

Wanna taste the Sweet feeling of being in Control?
Try [Sweet Control](http://sweet-control.fly.dev "Live Demo Version")!

:pick: [Sweet Control](http://sweet-control.fly.dev) is a responsive application, so You can try it on your computer or phone browser!

# Overview and Features

[⬆ Top](#)
:small_blue_diamond:
[Tech Stack](#tech-stack)
:small_blue_diamond:
[Update History](#update-history)
:small_blue_diamond:
[Roadmap](#roadmap)
:small_blue_diamond:
[License](#license)
:small_blue_diamond:
[Authors](#authors-and-acknowledgment)

:warning: In order to get access to all [Sweet Control](http://sweet-control.fly.dev) features, You need to register yourself. :warning:

The registration button can be found right on the index page (Fig. 1)!

![index](/src/main/resources/static/img/README/index.png)
:pushpin: Fig. 1 - Index Page View.

But don't worry! You just need to set a username and a password, no personal information required!

![registration](/src/main/resources/static/img/README/registration.png)
:pushpin: Fig. 2 - Registration Page View.

After your registration, You're now able to log in (Fig. 3) and get access to all the features shown at the top menu of your home page (Fig. 4).

![login](/src/main/resources/static/img/README/login.png)
:pushpin: Fig. 3 - Login Page View.

![home](/src/main/resources/static/img/README/home.png)
:pushpin: Fig. 4 - Home Page View.

The first step is to click Ingredient menu and select "Register Ingredient" (Fig. 4).

To register a new Ingredient You just need to set a "Name" and a "Measure Unity" (Fig. 5). Optionally, you can set a description as well. This last resource is useful to differentiate Ingredients with the same name.

![ingredientRegister](/src/main/resources/static/img/README/registerIngredient.png)
:pushpin: Fig. 5 - Register Ingredient Page View.

Selecting "View Ingredients" on the Ingredient menu, You'll see the list of registered Ingredients with their total amount (Fig. 6).

Note that they are sorted by alphabetical* order and split in pages with five* entries each. [[Check Roadmap](#roadmap)]

![ingredient](/src/main/resources/static/img/README/ingredient.png)
:pushpin: Fig. 6 - Ingredient List Page View.

You can filter Ingredients by their name! The filter searches by names containing the typed words/letters.

![ingredientFilter](/src/main/resources/static/img/README/ingredientFilter.png)
:pushpin: Fig. 7 - Ingredient Filtering.

The second step is to add some Inventories to your registered Ingredients! There are two options for that.

First one is to click "Register Inventory" at the bottom of the Ingredient List page (Fig. 6), opening a Generic Inventory Registration page (Fig. 8), where You need to select the Ingredient from a drop-down list. 

![registerGenericInventory](/src/main/resources/static/img/README/registerGenericInventory.png)
:pushpin: Fig. 8 - Register Generic Inventory Page View.

The other option is click "Inventories" beside the desired Ingredient in the Ingredient List page (Fig. 6).

Doing it so, You'll be lead to a Inventories List page (Fig. 9), detailing all the Inventories for that Ingredient.

Note that they are sorted, in order, by "Expiration Date", "Amount Left", "Price Per Amount" and Inclusion Date and split in pages with five* entries each. [[Check Roadmap](#roadmap)]

The "Delete Expired" button allows to quickly remove all expired Inventories!

![inventory](/src/main/resources/static/img/README/inventory.png)
:pushpin: Fig. 9 - Inventory List Page View.

By clicking "Register Inventory" at the bottom of the Inventories List page (Fig. 9), You'll be redirect to an Inventory Registration page (Fig. 10) for that specific Ingredient.

![registerInventory](/src/main/resources/static/img/README/registerInventory.png)
:pushpin: Fig. 10 - Register Inventory Page View.

In both cases (Figs. 8 and 10), It's needed to set "Total Amount" (i.e. the package total), "Amount Left" (i.e. the left amount in the package), total "Price" and "Expiration Date".

Next step is to register a Recipe! This can be done by selecting "Register Recipe" at the Recipe menu.

To register a new Recipe You just need to set a "Name" (Fig. 11). Optionally, you can set a description* as well. [[Check Roadmap](#roadmap)]

![registerRecipe](/src/main/resources/static/img/README/registerRecipe.png)
:pushpin: Fig. 11 - Register Recipe Page View.

Selecting "View Recipes" on the Recipe menu, You'll see the list of registered Recipes (Fig. 12), where You can check the Recipes next cooking  total cost as also how many more time You can cook it.

Note that they are, as well, sorted by alphabetical* order and split in pages with five* entries each. [[Check Roadmap](#roadmap)]

![recipe](/src/main/resources/static/img/README/recipe.png)
:pushpin: Fig. 12 - Recipe List Page View.

Clicking "Ingredients" beside any Recipe will display its list of Ingredients and will let You register new Ingredients to that Recipe!

To register a new Recipe Ingredient You just need to set an "Ingredient" and an "Amount" (Fig. 13).

![recipeIngredient](/src/main/resources/static/img/README/recipeIngredient.png)
:pushpin: Fig. 13 - Recipe Ingredients List.

Regarding Recipes still, you can filter them by their "Ingredients" (Fig. 14) and by their "Name" (Fig. 16)!

![recipeFilter1](/src/main/resources/static/img/README/recipeFilter1.png)
:pushpin: Fig. 14 - Recipe Filtering By "Ingredient".

![recipeFilter2](/src/main/resources/static/img/README/recipeFilter2.png)
:pushpin: Fig. 15 - Recipe Filtering By "Name".

At the Recipe List Page (Fig. 12) it is possible to check if the Recipe is "cookable" with your current Inventories. If there is a "Cook!" button at the Recipe entry, that means there are enough Inventories to cook it, otherwise, it will display a "Can't Cook!" button.

Clicking "Cook!" beside any "cookable" Recipe will register a cook event of that Recipe, updating Inventories that were used! You will be also redirect to the Cooked Recipes List (Fig. 16), where you can check the "Total Cost"s and "Making Date"s.

:warning: The order of Inventories used is the same displayed at Inventories List Page (Fig. 9), sorted ascending, in order, by "Expiration Date", "Amount Left", "Price Per Amount" and Inclusion Date.

![cookedRecipes](/src/main/resources/static/img/README/cookedRecipes.png)
:pushpin: Fig. 16 - Cooked Recipes List.

The "anti clockwise" icon lets You revert the cooking event, restoring the used Inventories.

The "magnifier" icon will display the event details (Fig. 17), where the used Inventories can be seen.

![cookedRecipes](/src/main/resources/static/img/README/cookedRecipesDetails.png)
:pushpin: Fig. 17 - Cooked Recipes Details.

:pick: Hovering over the :information_source: icon (Fig. 18) at any page will display the entry description if it was set during the registration. This is useful to differentiate same name entries.

![recipeDesc](/src/main/resources/static/img/README/recipeDesc.png)
:pushpin: Fig. 18 - Hovering over :information_source: icon.

# Tech Stack

[⬆ Top](#)
:small_blue_diamond:
[Overview](#overview-and-features)
:small_blue_diamond:
[Update History](#update-history)
:small_blue_diamond:
[Roadmap](#roadmap)
:small_blue_diamond:
[License](#license)
:small_blue_diamond:
[Authors](#authors-and-acknowledgment)

[Sweet Control](http://sweet-control.fly.dev) was built in Java with the Spring framework (Spring Boot & Spring Security) and MVC design pattern.

![Java](https://res.cloudinary.com/practicaldev/image/fetch/s--KR6jSVNe--/c_limit%2Cf_auto%2Cfl_progressive%2Cq_auto%2Cw_880/https://img.shields.io/badge/Java-ED8B00%3Fstyle%3Dfor-the-badge%26logo%3Djava%26logoColor%3Dwhite)
![Spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![SpringBoot](	https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring-boot)
![SpringSecurity](https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=Spring-Security&logoColor=white)

Data are stored in a MySQL database. Persistence and validation was done with Hibernate (through Spring Data).

![MySQL](https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white)

Application front-end was built with Thymeleaf template engine along with HTML, CSS and JavaScript.

![Thymeleaf](https://img.shields.io/badge/Thymeleaf-%23005C0F.svg?style=for-the-badge&logo=Thymeleaf&logoColor=white)
![HTML](https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=html5&logoColor=white)
![CSS](https://img.shields.io/badge/CSS3-1572B6?style=for-the-badge&logo=css3&logoColor=white)
![JS](https://img.shields.io/badge/JavaScript-323330?style=for-the-badge&logo=javascript&logoColor=F7DF1E)

Bootstrap framework was used for more complex styling and responsiveness. Font Awesome provided icons.

![Bootstrap](https://img.shields.io/badge/Bootstrap-563D7C?style=for-the-badge&logo=bootstrap&logoColor=white)
![FA](https://img.shields.io/badge/Font_Awesome-339AF0?style=for-the-badge&logo=fontawesome&logoColor=white)

# Update History

[⬆ Top](#)
:small_blue_diamond:
[Overview](#overview-and-features)
:small_blue_diamond:
[Tech Stack](#tech-stack)
:small_blue_diamond:
[Roadmap](#roadmap)
:small_blue_diamond:
[License](#license)
:small_blue_diamond:
[Authors](#authors-and-acknowledgment)

* 0.1.0
    * First stable release.

# Roadmap

[⬆ Top](#)
:small_blue_diamond:
[Overview](#overview-and-features)
:small_blue_diamond:
[Tech Stack](#tech-stack)
:small_blue_diamond:
[Update History](#update-history)
:small_blue_diamond:
[License](#license)
:small_blue_diamond:
[Authors](#authors-and-acknowledgment)

The project is still under development and the next updates will focus on the following tasks:

- [ ] Documentation (Javadoc);
- [ ] Unit Testing (JUnit);
- [ ] Add OAuth;

User:

- [ ] Add User Roles;
- [ ] Add User Company/Group;
- [ ] Add User Password Reset;

Features:

- [ ] Custom error Views;
- [ ] Variable number of entries per page;
- [ ] Variable sorting type of entries listing;
- [ ] Recipe Directions Field;
- [ ] Reports.


# License

[⬆ Top](#)
:small_blue_diamond:
[Overview](#overview-and-features)
:small_blue_diamond:
[Tech Stack](#tech-stack)
:small_blue_diamond:
[Update History](#update-history)
:small_blue_diamond:
[Roadmap](#roadmap)
:small_blue_diamond:
[Authors](#authors-and-acknowledgment)

Licensed under the terms of the MIT License.
See [LICENSE](https://github.com/leonardovcl/sweet-control/blob/main/LICENSE) for more information.

# Authors and Acknowledgment

[⬆ Top](#)
:small_blue_diamond:
[Overview](#overview-and-features)
:small_blue_diamond:
[Tech Stack](#tech-stack)
:small_blue_diamond:
[Update History](#update-history)
:small_blue_diamond:
[Roadmap](#roadmap)
:small_blue_diamond:
[License](#license)

:heavy_check_mark: 
**Leonardo Viana**

[![Github](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/leonardovcl/ "leonardovcl")
[![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/leonardovcl/ "LinkedIn")
[![Gmail](https://img.shields.io/badge/Gmail-D14836?style=for-the-badge&logo=gmail&logoColor=white)](mailto:leonardovc.lima@gmail.com "leonardovc.lima@gmail.com")
