FXUtils
=======

Library providing simple utilities for commons tasks in JavaFX, contains of several modules including:

* spring integration
* validation
* binding
* conversions

## Core utils

Common problem is that gui elements cannot be directly changed from other threads. FXUtils comes with FXUpdater.
Assuming that model represents object binded with GUI control, simple usage is as follows:

    FXUpdater fxUpdater = FXUpdaterUtils.getFxUpdater();
    fxUpdater.update(model::setValue, 10);

It can be used also as implemented interface:

    public class SimpleService extends Service<String> implements FXUpdater {

        @Override
        protected Task<String> createTask() {
            return new Task<String>() {
                @Override
                protected String call() throws Exception {
                    update(model::setValue, 10);
                }
            };
        }

    }

## Converter

Another problem is connected with translating object binded with GUI to normal object without properties. Here comes FXConverter.
FXConverter can works in non FX Thread. Assuming that we have following model binded with gui:

    public class Model {

        @FXConvert(Person.class)
        private final StringProperty name = new SimpleStringProperty();

        @FXConvert(value = Person.class, getterSetterNames = "forName")
        private final StringProperty forname = new SimpleStringProperty();

        @FXConvert(Data.class)
        private final StringProperty address = new SimpleStringProperty();

        @FXConvert(getterSetterNames = {"ID", "setNewID", getterSetterElements = {Element.FIELD, Element.METHOD}, setterType = long.class)
        private final LongProperty id = new SimpleLongProperty();

        private final IntegerProperty number = new SimpleIntegerProperty();
    }

And then following normal objects:

    public class Person {

        private String name;
        private String forName;
        private long ID;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getForName() {
            return forName;
        }

        public void setForName(String forName) {
            this.forName = forName;
        }

        public long getID() {
            return ID;
        }

        public void setNewID(long ID) {
            this.ID = ID;
        }

    }

    public class Data {

        private String address;
        private long ID;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public long getID() {
            return ID;
        }

        public void setNewID(long ID) {
            this.ID = ID;
        }

    }

We can perform conversions:

    FXConverter fxConverter = FXConverters.getFxConverter();

    Person person = fxConverter.convertFromModelToData(model, Person.class);

    Data data = new Data;
    fxConverter.convertFromModelToData(model, data);

    Model model = fxConverter.convertFromDataToModel(person, Model.class)
    fxConverter.convertFromDataToModel(person, model)

## Spring integration

Spring integration comes with custom component annotations and special FXSpringFXMLLoader.

    @Configuration
    @ComponentScan
    public class Config {

        @Bean
        public ResourceBundle resources() {
            return ResourceBundle.getBundle("bundle/bundle");
        }

        @Bean
        public URL location() {
            return getClass().getResource("/fxml/Controller.fxml");
        }

    }

    public class SimpleApplication extends Application {

        @Override
        public void start(Stage primaryStage) throws Exception {
            ApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
            URL location = (URL) applicationContext.getBean("location");
            ResourceBundle resources = (ResourceBundle) applicationContext.getBean("resources");
            FXMLLoader fxmlLoader = new FXMLLoader(location, resources);

            FXSpringFXMLLoader fxSpringFXMLLoader = new FXSpringFXMLLoader.FXSpringFXMLLoaderBuilder(fxmlLoader, applicationContext)
                    .build();

            Parent parent = fxSpringFXMLLoader.load();
            Scene scene = new Scene(parent);
            primaryStage.setScene(scene);
            primaryStage.show();
        }

        public static void main(String[] args) {
            launch(args);
        }
    }

    @FXController
    public class Controller {

        @Autowired
        private Model model;

        @Autowired
        private Service<Void> service;

    }

    @FXModel
    public class Model {

    }

    @FXService
    public class SimpleService extends Service<String> {

        @Autowired
        private Model model;

        @Autowired
        private ResourceBundle resources;

    }

### Next modules need Spring configuration to work

## Binding

FXUtils comes with FXBinding, annotation based properties binding. It requires inject FXExtension in controller and start and finish it in initialize method.


    @Configuration
    @ComponentScan
    @EnableFXBinding
    public class Config {

    }

    @FXController
    public class Controller {

        @FXML
        @FXBinding(field = "service", property = "text", propertySecond = "value", direction = Direction.TARGET)
        private Label serviceLabel;

        @FXML
        @FXBinding(field = "model", property = "text", propertySecond = "name", direction = Direction.TARGET)
        private Label nameLabel;

        @FXML
        @FXBinding(field = "model", property = "text", propertySecond = "surname", direction = Direction.TARGET)
        private Label surnameLabel;

        @FXML
        @FXBinding(field = "model", property = "text", stringConverter = NumberStringConverter.class)
        private TextField inputField;

        @Autowired
        private Service<String> service;

        @Autowired
        private Model model;

        @Autowired
        private FXExtension fxExtension;

        @FXML
        public void initialize() {
            fxExtension.startFXInitialize(this);
            fxExtension.finishFXInitialize(this);
        }

    }

    @FXModel
    public class Model {

        private final StringProperty name = new SimpleStringProperty();

        private final StringProperty surname = new SimpleStringProperty();

        private final IntegerProperty text = new SimpleIntegerProperty();

    }

## Validation

FXUtils contains annotation based validation module, which combines two approach together:

* [Bean Validation](http://beanvalidation.org/) with [Hibernate Validator 5.2.0.Beta1](http://docs.jboss.org/hibernate/validator/5.2/reference/en-US/html/) for properties model
* Validation for control is provided by [ControlFX](http://fxexperience.com/controlsfx/features/#decorationvalidation)

Validated field must be first binded with appropriates properties in model. Properties description in @FXValidation can be skipped if there is @FXBinding wit those value presented
Validator uses messages from Bean ResourceBundle.
It requires inject FXExtension in controller and start and finish it in initialize method.

    @Configuration
    @ComponentScan
    @EnableFXBinding
    @EnableFXValidation
    public class Config {

        @Bean
        public ResourceBundle resources() {
            return ResourceBundle.getBundle("bundle/bundle");
        }

        @Bean
        public ValidationSupport validationSupport() {
            return new ValidationSupport();
        }

    }

    @FXController
    public class Controller {

        @FXML
        @FXBinding(field = "service", property = "text", propertySecond = "value", direction = Direction.TARGET)
        private Label serviceLabel;

        @FXML
        @FXBinding(field = "model", property = "text", propertySecond = "name")
        @FXValidation("validationSupport")
        private TextField nameField;

        @FXML
        @FXBinding(field = "model", property = "text", propertySecond = "surname")
        @FXValidation("validationSupport")
        private TextField surnameField;

        @FXML
        @FXBinding(field = "model", property = "text", propertySecond = "address")
        @FXValidation(model = "model", property = "surname", required = false)
        private TextField addressField;

        @FXML
        @FXBinding(field = "validationSupport", property = "disable", propertySecond = "invalid", direction = Direction.TARGET)
        private Button startButton;

        @Autowired
        private Service<String> service;

        @Autowired
        private Model model;

        @Autowired
        private ValidationSupport validationSupport;

        @Autowired
        private FXExtension fxExtension;

        @FXML
        public void initialize() {
            fxExtension.startFXInitialize(this);
            fxExtension.finishFXInitialize(this);
        }

        @FXML
        void startButtonClicked() {
            service.restart();
        }

    }

    @FXModel
    public class Model {

        @UnwrapValidatedValue
        @NotEmpty(message = "{error.empty,name}")
        @Size(max = 20)
        private final StringProperty name = new SimpleStringProperty();

        @UnwrapValidatedValue
        @NotEmpty(message = "{error.empty.surname}")
        @Size(max = 4, message = "Wrong size")
        private final StringProperty surname = new SimpleStringProperty();

        @UnwrapValidatedValue
        @NotEmpty(message = "{error.empty.address}")
        @Size(max = 4)
        private final IntegerProperty address = new SimpleIntegerProperty();

    }

## Initial values

FXUtils lets to set initial values by annotation. Properties description in @FXDefault can be skipped if there is @FXBinding wit those value presented
It requires inject FXExtension in controller and start and finish it in initialize method.

    @Configuration
    @ComponentScan
    @EnableFXDefault
    public class Config {

    }

    public class SurnameSupplier implements Supplier<String> {

        @Override
        public String get() {
            return "Kowalski";
        }

    }

    public enum MyEnum {
        MY_ENUM_ONE, MY_ENUM_TWO
    }

    @FXController
    public class Controller {

        @FXML
        @FXDefault(property = "text", defaultString = "Jan")
        private TextField nameField;

        @FXML
        @FXBinding(field = "model", property = "text", propertySecond = "surname")
        @FXDefault(defaultSupplier = SurnameSupplier.class)
        private TextField surnameField;

        @FXML
        @FXDefault(property = "value", defaultEnum = MyEnum.class, defaultString = "MY_ENUM_ONE")
        private ComboBox<MyEnum> enumComboBox;

        @Autowired
        private Model model;

        @Autowired
        private FXExtension fxExtension;

        @FXML
        public void initialize() {
            fxExtension.startFXInitialize(this);
            fxExtension.finishFXInitialize(this);
        }

    }

## Injecting custom variables

FXUtils allows to inject object, which aren't Spring Beans. It was mainly focused on injecting primaryStage into Controllers. Field to inject Stage must e named stage.
It also allows to injecting custom objects, provided that field names corresponds to the one provided in builder.

    @Configuration
    @ComponentScan
    @EnableFXAutowired
    public class Config {

        @Bean
        public ResourceBundle resources() {
            return ResourceBundle.getBundle("bundle/bundle");
        }

        @Bean
        public URL location() {
            return getClass().getResource("/fxml/Controller.fxml");
        }

    }

    public class SimpleApplication extends Application {

        @Override
        public void start(Stage primaryStage) throws Exception {
            ApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
            URL location = (URL) applicationContext.getBean("location");
            ResourceBundle resources = (ResourceBundle) applicationContext.getBean("resources");
            FXMLLoader fxmlLoader = new FXMLLoader(location, resources);

            FXSpringFXMLLoader fxSpringFXMLLoader = new FXSpringFXMLLoader.FXSpringFXMLLoaderBuilder(fxmlLoader, applicationContext)
                    .withFXSpringComponent(new FXAutowiredSpringComponent.FXAutowiredSpringComponentBuilder()
                            .withStage(primaryStage)
                            .withObject("myObject", new MyObject())
                            .build())
                    .build();

            Parent parent = fxSpringFXMLLoader.load();
            Scene scene = new Scene(parent);
            primaryStage.setScene(scene);
            primaryStage.show();
        }

        public static void main(String[] args) {
            launch(args);
        }
    }

    @FXController
    public class Controller {

        @FXAutowired
        private Stage stage;

        @FXAutowired
        private MyObject myObject;

    }

## Tools

FXUtils Spring integration allows to inject FXConverter and FXUpdater as normal beans. Only config needs two new annotations.

    @Configuration
    @ComponentScan
    @EnableFXUpdater
    @EnableFXConverter
    public class Config {

    }