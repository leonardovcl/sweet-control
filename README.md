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

> Sweet Control is a web-based system designed to help with food ingredients inventory management.

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

## :hammer: User Authentication

:warning: In order to get access to all [Sweet Control](http://sweet-control.fly.dev) features, You need to register yourself. :warning:

Don't worry! You just need to set a username and a password, no personal information required!

> :information_source: The registration button can be found right at the [index page](http://sweet-control.fly.dev).

After registration, You can log in and get access to all features shown at the top menu of your home page!

![registration-login](/src/main/resources/static/img/README/registration2login.gif)
:pushpin: Registration and Log in.

## :hammer: Ingredients and Inventories

> :information_source: To deal with ingredients, the first step is to click Ingredient menu and select "Register Ingredient".

To register a new Ingredient You just need to set a "Name" and a "Measure Unity". Optionally, you can set a description as well. This last resource is useful to differentiate Ingredients with the same name.

![ingredientRegister](/src/main/resources/static/img/README/registerIngredient.gif)
:pushpin: How to Register Ingredients.

> :information_source: Selecting "View Ingredients" on the Ingredient menu, You'll see the list of registered Ingredients with their total amount.

:bookmark_tabs: Note that they are sorted by alphabetical* order and split in pages with five* entries each. *[[Check Roadmap](#roadmap)]

You can filter Ingredients by their name! The filter searches by names containing the typed words/letters.

![ingredient](/src/main/resources/static/img/README/viewIngrdient.gif)
:pushpin: Ingredient Listing and Filtering.

Next step is to add some Inventories to your registered Ingredients! There are two options for that!

In order to register an Inventory, It's needed to set "Total Amount" (i.e. the package total), "Amount Left" (i.e. the left amount in the package), total "Price" and "Expiration Date".

> :information_source: By clicking "Register Inventory" at the bottom of the Ingredient List page, a Generic Inventory Registration page will be loaded, where You need to select the Ingredient type from a drop-down list.

![registerGenericInventory](/src/main/resources/static/img/README/registerInventory1.gif)
:pushpin: How to Register a Generic Inventory.

> :information_source: By clicking "Inventories" beside the desired Ingredient in the Ingredient List page, You'll be also lead to a Inventories List page, detailing all the Inventories for that Ingredient.

:bookmark_tabs: Note that they are sorted, in order, by "Expiration Date", "Amount Left", "Price Per Amount" and Inclusion Date and split in pages with five* entries each. *[[Check Roadmap](#roadmap)]

> :information_source: The "Delete Expired" button allows to quickly remove all expired Inventories!

![inventory](/src/main/resources/static/img/README/viewInventory.gif)
:pushpin: Inventory Listing and Delete Expired.

> :information_source: By clicking "Inventories" beside the desired Ingredient and then clicking "Register Inventory" at the bottom of the page, You'll be redirect to an Inventory Registration page for that specific Ingredient. And there You go, your second registration option!

![registerGenericInventory](/src/main/resources/static/img/README/registerInventory2.gif)
:pushpin: How to Register an Inventory.

## :hammer: Recipes

Next, keep going and register some of your Recipes!

> :information_source: This can be done by selecting "Register Recipe" at the Recipe menu.

To register a new Recipe You just need to set a "Name". Optionally, you can set a description* as well. *[[Check Roadmap](#roadmap)]

![registerRecipe](/src/main/resources/static/img/README/registerRecipe.gif)
:pushpin: How to Register a Recipe.

> :information_source: Selecting "View Recipes" on the Recipe menu, You'll see the list of registered Recipes, where You can check the Recipes next cooking total cost as also how many more time You can cook it!

:bookmark_tabs: Note that they are, as well, sorted by alphabetical* order and split in pages with five* entries each. *[[Check Roadmap](#roadmap)]

Also, You can filter them by their "Ingredients" and by their "Name"!

![recipe](/src/main/resources/static/img/README/viewRecipe.gif)
:pushpin: Recipe Listing and Filtering.

> :information_source: Clicking "Ingredients" beside any Recipe will display its list of Ingredients and will let You register Ingredients to that Recipe!

To register a new Recipe Ingredient You just need to set an "Ingredient" and an "Amount".

![recipeIngredient](/src/main/resources/static/img/README/detailRecipe.gif)
:pushpin: How to register Recipe Ingredients.

## :hammer: Cooked Recipes

> :information_source: At the Recipe List Page it is possible to check if the Recipe is "cookable" with your current Inventories. If there is a "Cook!" button at the Recipe entry, that means there are enough Inventories to cook it, otherwise, it will display a "Can't Cook!" button.

Clicking "Cook!" beside any "cookable" Recipe will register a cook event of that Recipe, updating Inventories that were used! You will be also redirect to the Cooked Recipes List page, where you can check the "Total Cost"s and "Making Date"s.

:bookmark_tabs: Note that they are sorted by "Making Date" and split in pages with five* entries each. *[[Check Roadmap](#roadmap)]

> :information_source: The "magnifier" icon will display the event details, where the used Inventories can be seen.

:warning: The order of Inventories used is the same displayed at Inventories List Page, sorted ascending, in order, by "Expiration Date", "Amount Left", "Price Per Amount" and Inclusion Date.

![cookedRecipes](/src/main/resources/static/img/README/viewCookedRecipe.gif)
:pushpin: How to Register a Cook Recipe Event.

You can filter Cooked Recipes by their "Ingredients" and by their "Name", just like with Recipes!

![cookedRecipes](/src/main/resources/static/img/README/filterCookedRecipe.gif)
:pushpin: Cooked Recipe Listing and Filtering.

> :information_source: The "anti clockwise" icon lets You revert the cooking event, restoring the used Inventories.

![cookedRecipes](/src/main/resources/static/img/README/revertCookedRecipe.gif)
:pushpin: Revert Cooked Recipe.

## :pick: Information Icons

> :information_source: Hovering over the :information_source: icon at any page will display the entry description if it was set during the registration. This is useful to differentiate same name entries.

![recipeDesc](/src/main/resources/static/img/README/detailsIcon.gif)
:pushpin: Hovering over :information_source: icon.

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
