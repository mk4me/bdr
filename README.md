Motion Database (BDR) - server component
===============================================

This repository implements a server component for a Motion Database (BDR) system. It utilizes web services (SOAP, WSDL) for communication and stores data in a relational database with functionalities for access control and data management. (Keywords: BDR, motion data, web services, database, XML, API)


Architecture Document
=====================
There is guidance within this template that appears in a style named
InfoBlue. This style has a hidden font attribute that allows you to
toggle whether it is visible or hidden in this template. Use the
Microsoft® Word® menu **Tools &gt; Options &gt; View &gt; Hidden Text**
check box to toggle this setting. There is also an option for printing:
**Tools &gt; Options &gt; Print**.

Use
===

The document describes the philosophy, decision, limitations,
justifications, essential elements and all other significant aspects of
the system that affect the shape of its design and implementation.

Goals and philosophy of the system architecture
===============================================

The "Motion" system aims to develop a variety of motion processing
means, most of which are orthogonal to the application domains of motion
data. Therefore, the Motion Database (BDR) component must also be able
to collect motion data for a wide range of applications; In particular:

-   Medicine – focused on the analysis of the functioning of the
    > musculoskeletal system from the point of view of orthopaedics.

-   Entertainment – processing of motion data for the purpose of
    > creating animations (games, advertisements).

-   Scientific Research – psychology (behaviorism, virtual reality),
    > cognitive sciences, motion analysis for computer vision.

Therefore, the key features of a BDR component must be universality,
evolutionary and a high level of independence from specific commercial
tools.

The long-term vision of the entire system, in which the appropriate
shape of the BDR component plays an important role, envisages the
creation of a platform that collects multimodal motion data from various
laboratories and a set of tools enabling not only comprehensive search
of motion data, but also data drilling, aimed at finding regularities
within appropriately classified data. Related to this concept is also
the vision of forming a community of creators and consumers of motion
data, including motion acquisition laboratories and motion research
entities. From the point of view of the research trend, the concept of
further development should include the possibility of extensions with
various data analysis mechanisms. On the other hand, in the field of
commercial applications, the prospect of creating a mechanism for
selling motion data should be taken into account.

For these reasons, it was decided to implement the component's
architecture as service-oriented, using open standards to enable
effective remote access to its functionality.

Assumptions and dependencies
============================

The component is created in parallel with the other products of the
project and as such is not limited by the requirements of integration
with inheritance software.

The diversity of implementation platforms of the currently developed
client components, as well as the requirement of openness to integration
with components operating remotely, created in the future, led to the
choice of Web services interfaces as the basis for interoperability with
client software.

An important requirement is the differentiation of access rights to
motion data – both in terms of modification and read. Because
authorization rules can depend on a number of properties of a data
instance, the decision to grant access is entrusted to the appropriate
database functions and stored procedures.

On the other hand, protection against penetration or overload of the
server component by requests from unauthorized users requires the
introduction of authentication in the web services implementation layer,
performed at the call stage.

Another important assumption is the design approach to the problem of
personal data management. It was decided to extract sensitive data,
including surnames, first names and medical information, into separate
modules using separate database instances, which could be run as the
property of the relevant health care facilities, while the motion data
recorded in connection with the research would remain in the central
part of the BDR in an anonymized form. The ability to inspect anonymised
motion samples and its scope will remain – as for all other BDR data –
to be decided by their owners and will be subject to individually
granted permissions.

Requirements Affecting Architecture
===================================

With regard to the types of functionality offered and the level of
sophistication of the software's support for the user's activities, the
following issues can be distinguished:

-   Minimum level – cataloguing and classifying the motion data recorded
    > by the laboratory, based on parameters important from the point of
    > view of searching and analysis of the data set.

-   Providing complete motion data within the above-mentioned catalogue.

-   Remote access to the dataset for an approved group of employees and
    > research partners.

-   Download and update a shallow copy of the database to enable certain
    > client software tasks to be performed in disconnected mode and to
    > minimize the number of distant calls.

-   Individualized perspectives for viewing and searching data.

-   A data extensibility mechanism that allows you to dynamically enrich
    > the set of descriptive data in a live system and configure its
    > view depending on the needs of users.

-   Non-text motion property viewer elements available for selecting
    > instances from search lists.

-   Cooperation with other server components – e.g. for the purpose of
    > selling selected motion samples.

-   Ability to run server-side analytics tasks that require visibility
    > into motion data files.

The above functionality should be developed taking into account the
following non-functional features assumed by the concept of the system:

-   High genericity of data structures and access mechanisms, providing
    > support for diverse areas of application of the collected motion
    > data and flexibility to expand the data set describing these
    > resources.

-   Collect motion data based on standard formats, and equip it with
    > adequate descriptions of measurement configurations, allowing the
    > data to be read and interpreted by other configurations of the
    > utility software than those used in the laboratory where the
    > motion was recorded.

-   Platform independence – availability of data for client software
    > implemented in various programming technologies.

-   Authorization mechanisms that differentiate access rights to motion
    > resources depending on the identity of the entity executing the
    > request.

-   Consistency and security of the data collected.

Decisions, limitations and justifications
=========================================

The direct context of the component consists of software modules for
reading, processing and synthesizing motion data, which can be
characterized by various technologies and implementation platforms.
Hence, communication with them was based on open standards of Web
services.

In addition, for selected elements of the component's functionality, it
is considered to provide the interface in the form of web pages consumed
by web browsers or by the appropriate preview functionality in
stand-alone client tools. This should, thanks to the standard format,
reduce the workload when implementing the presentation layer for the
data downloaded in this way.

The rest of the context will be components that work together locally on
the server. For these components, a local interface will be available,
allowing direct connection to the database management system interface
and calling selected stored procedures.

Architectural mechanisms
========================

Service-oriented architecture
-----------------------------

In accordance with the above premises, it was adopted as an
architectural model based on stateless web services, using the SOAP
protocol and described in the WSDL language. Due to this type of
interface, the results of queries to the database performed for the
purpose of query operations in web services are immediately generated,
i.e. at the DBMS level, in the form of XML structures.

Storing Files in a Database Management System
---------------------------------------------

A good level of support for large binary objects (BLOBs) in the selected
DBMS prompted the authors to collect BDR files directly under the
control of the DBMS. This simplifies some programming and administrative
operations (e.g. backup, authorization).

Isolation of data with the stored procedures layer
--------------------------------------------------

Due to the fact that access to individual data instances may vary
depending on the explicitly assigned permissions and other
characteristics of resources (e.g. owner/author), it is necessary to use
rules based on the state of the database. In addition, there are data
consistency rules beyond referential integrity and database typology,
which are convenient to secure by mediating update activities through
the stored procedures layer.

Generic attributes and other generic elements
---------------------------------------------

The BDR structure requires a high degree of flexibility and
extensibility. Therefore, instead of trying to exhaustively include all
potentially needed attributes at the schematic design stage, it was
decided to introduce frameworks that allow the metadata to be defined.
These constructions include:

-   named Session Groups to which many-to-many Sessions can be assigned;

-   definable attributes that can describe Sessions, Measurement Tests,
    Performer Configurations, Performers, Measurement Configurations, as
    well as Files; attribute values can be values of selected simple
    types and identifiers of Files entered into BDR;

-   Attribute Groups, into which attributes can be grouped according to
    their fields of application.

In addition, in order to provide generic solutions for many types of
assets in a uniform way, the concepts of Proof, Performer, File,
Measurement Configuration and Performer Configuration have been
generalized to the abstract Asset class. The resulting conceptual scheme
is shown in the section Main abstractions.

Main abstractions
=================

The main abstractions of the system are entities representing motion
records. Key entities such as Session, Sample, Measurement
Configuration, Performer, Performer Configuration, and File are modeled
as specializations of the Asset concept, at the level of which the
generic attribute mechanism is provided. These concepts are presented in
the UML diagram below.

Architectural layers or frame
=============================

In the implementation of the architecture, it is necessary to
distinguish the layers (listed from the innermost ones):

-   relational database with the ability to collect large binary
    objects,

-   functions, stored procedures and database triggers that guard
    consistency and authorization in data access and encapsulate
    queries,

-   implementation of the service interface, implemented in an
    object-oriented programming language; In most tasks, the complexity
    of this layer has been reduced to a minimum, entrusting a
    significant part of the application logic to the database procedures
    layer,

-   XML data representations used in web service operation signatures

-   authentication rules in web service interfaces.

The following diagram illustrates the general architecture diagram.

Architectural perspectives
==========================

Logical perspective
-------------------

At the conceptual level, it is necessary to distinguish two main parts
of the schema, separated for domain and formal reasons into two separate
databases. In addition to the already discussed structure of motion
resources, it is a set of basic medical data, including the identity of
patients, information about detected diseases and examinations, and a
(one-way) link between these and the motion data collected in the
general part of the BDR. These concepts are presented in the UML diagram
below.

Operational perspective
-----------------------

The figure below shows the implementation diagram of the BDR server part
along with the dependencies between the components. The implementation
of the medical part of the base has an analogous form.

Use Case Perspective
--------------------

### Actors

In the use case model, the following actors were identified, identified
by the system based on authentication and the way they communicate with
the functionality of the system. Because the server component described
in this document represents the resource and logic layers of the
application, it is not directly interacted with users, but rather with
the client components that represent them. The actors of the BDR server
component are:

-   Data Viewing Client (P)

> Represents the basic set of permissions in remote access. Its actions
> do not lead to modification of the motion data. Read rights are
> understood to include listing available content, searching,
> navigating, and downloading files. They are subject to restrictions
> resulting from the imposed system of permissions for individual
> sessions.

-   Data Update Client (U)

> It has the authority to modify and create new instances of motion
> data. Upgrade rights also include the ability to upload and replace
> files. For sessions created by a given user, i.e. for sessions that a
> given user owns, it is possible to determine the level of their
> visibility and individual permissions for individual other users. They
> are subject to restrictions resulting from the imposed system of
> permissions for individual sessions.

-   Local Update Client (LU)

> It communicates with the BDR server from the database owner's local
> network, bypassing the Web services interface layer. It implements the
> functionality of updating data through one of the following
> mechanisms:

-   Calling the local façade functionality within the server's local
    address space, or in a remote call, via middleware technology.

-   A direct command to the database management system is invoked, but
    only in the form of a stored procedure.

-   entering new data into a dedicated catalog, leading to their
    consumption by the BDR component functionality monitoring this
    catalog, used to upload and modify data.

<!-- -->

-   Administrative Client (A)

> It has the ability to modify and expand metadata – including session
> groups, motion types, attribute groups, attributes and their
> enumeration values. In addition, it has the ability to create new
> users and define user rights for all sessions, i.e. rights analogous
> in this respect to the owner of a given session.

The actor letter symbols assigned above are assigned to the use cases
defined below, either at the individual case level (next to their names)
or within the header of a given use case category.

### Use Cases – Data Viewing (P)

#### Authorized Request (Abstract)

It represents any order sent to the database via the Web services
interface, as all these requests are covered by the authentication
mechanism and require assigning the identity of a given user to the
appropriate group in the server configuration.

#### Generic query

The client provides the service with a data structure called a filter,
containing query criteria formulated in the spirit of the QBE approach.

#### Retrieving resource data by ID (abstract)

The scenario consists in indicating – for a specific case – the
identifier of the resource whose data will be retrieved, provided that
the identity of the client formulating the request entitles it to do so.
The concretizations of this use case apply to the following types of
resources:

-   Performer

-   Session

-   Sessions with subordinate resources

-   Session labels

-   Attempts

-   Measurement Configuration

-   Performer Configuration

-   File

#### Retrieve Global Resource List (abstract)

The scenario consists of choosing one of the following specific cases.
The response returns a list of resources, taking into account the
permissions of the requesting entity. The concretizations of this use
case apply to the following types of resources:

-   Performers

-   Laboratories

-   Session Groups

-   Measurement configurations

-   Attribute Groups

-   Types of motion

-   Sessions with subordinate resources

#### Retrieving a list of related resource data (abstract)

The scenario involves selecting one of the following specific cases,
which involves indicating the instance of the resource that is the
source of navigation and the type of resource that is the purpose of
navigation. The response returns a list of related resources, taking
into account the permissions of the requester. The concretizations of
this use case apply to the following types of navigational bindings:

-   Performers performing in a given Session

-   Performers performing in Sessions from a given Laboratory

-   Session performed by a given Performer

-   Sessions belonging to a given Session Group

-   Sessions using a given Measurement Configuration

-   Session Groups comprising a given Session

-   Measurement tests belonging to a given Session

-   Measurement Configurations used in a given Session

-   Files that belong to a given resource of a given type

-   Files associated with a given resource of a given type as its
    attributes

#### Download the file

Allows you to download a file based on the numeric ID you provide. The
success of the operation depends on the fact that the principal ordering
the download has read permissions for a given session. This is one of
the few use cases that consists of more than one interaction step. The
full flow includes:

> 1\. Calling a File Preparation Request by an Operation
>
> 2\. Copy the file by the BDR component to the dedicated FTPS file
> transfer area and specify the path in the response
>
> 3\. Client download the file using FTPS protocol
>
> 4\. Triggering an operation by the client to confirm the successful
> receipt of the file
>
> 5\. BDR removes the shared file from the download area.

#### Downloading a Shallow BDR Copy

Download an XML file that contains data about instances of the main BDR
entities, including files, but omits the contents of the files
themselves. The flow is analogous to the mechanism of downloading
regular files, i.e. it includes:

> 1\. Calling a File Preparation Request by an Operation
>
> 2\. Copy the file by the BDR component to the dedicated FTPS file
> transfer area and specify the path in the response
>
> 3\. Client download the file using FTPS protocol
>
> 4\. Triggering an operation by the client to confirm the successful
> receipt of the file
>
> 5\. BDR removes the shared file from the download area.

#### Retrieving BDR metadata

Download an XML file that contains metadata data about the main BDR
entities. The flow is analogous to the mechanism of downloading regular
files, i.e. it includes:

> 1\. Calling a File Preparation Request by an Operation
>
> 2\. Copy the file by the BDR component to the dedicated FTPS file
> transfer area and specify the path in the response
>
> 3\. Client download the file using FTPS protocol
>
> 4\. Triggering an operation by the client to confirm the successful
> receipt of the file
>
> 5\. BDR removes the shared file from the download area.

### Use Cases – Data Update (U)

#### Session Fileset Validation

A use case of pure data read, but only for the purpose of updating
functionality. It accepts a list of file names to be entered into the
database, and returns in response a skeleton of the session data
structure and its measurement attempts, filled with attribute names that
can be determined on the basis of a naming convention correctly used in
the file set under consideration. If errors are found, the returned
structure will contain error messages.

#### Creating a Performer

Creates a performer instance with the specified ID value. Due to the
anonymization of the central part of the BDR, no other attributes are
included here.

#### Session Creation

Creates an instance of a session, specifying its name, description,
tags, groups to which it is assigned, lab ID, and recording date.

#### Creating a Sample

Creates a measurement sample for the specified session to which the
sample belongs. Specifies the name and description of the sample.

#### Assigning a Session to a Session Group

Allows you to assign a Session to one of the Session Groups previously
defined in BDR.

#### Creating a Performer Configuration

Links the Performer specified by the ID to the Session specified by the
ID. The resulting instance of the Performer Configuration can be used to
specify additional values for the attributes defined for it.

#### Setting an Attribute value for a Resource (abstract)

It allows you to modify or add the value of the indicated attribute –
including a generic attribute, i.e. one that can be defined during the
operation of the system and does not have a statically dedicated field
in each instance of the entity. The following entities can be modified:

-   Performer

-   Session

-   Measurement test

-   Measurement Configuration

-   Performer Configuration

-   File

#### Deleting an Attribute value for a Resource

Deletes the generic attribute instance for the specified resource. The
input data is the identifier of the resource being modified and an
indication of the type of resource.

#### Setting the File Type Attribute value for an Asset

Creates or modifies the value of an attribute whose type is the ID of a
file in the database.

#### Entering a single File belonging to an Asset (abstract)

It allows you to upload a file and associate it with the resource
indicated by the identifier as a subordinate file of this resource. The
flow for this case involves FTPS communication and consists of the
following steps:

> 1\. Upload the file to the server area through an authorized client.
>
> 2\. Invoking an operation that orders the upload of a file
>
> 3\. Uploading the file via the BDR server component
>
> 4\. Deletion of the file from the file transfer area on the server disk
> by the BDR server component.

Specifications of this case allow you to upload files belonging to:

-   Measurement Configuration

-   Session

-   Measurement test

#### Entering a group of files belonging to an Asset (abstract) 

It allows you to upload a set of files and associate them with the
resource indicated by the ID, as files subordinate to this resource.

The flow for this case involves FTPS communication and consists of the
following steps:

> 1\. Upload the file to the server area through an authorized client.
>
> 2\. Invoking an operation that orders the upload of a file
>
> 3\. Uploading the file via the BDR server component
>
> 4\. Deletion of the file from the file transfer area on the server disk
> by the BDR server component.

Specifications of this case allow you to upload files belonging to:

-   Measurement Configuration

-   Session

-   Measurement test

#### Changing the contents of a file

Allows you to replace a file in the database. The flow for this case
involves FTPS communication and consists of the following steps:

> 1\. Upload the file to the server area through an authorized client.
>
> 2\. Calling the file swap operation
>
> 3\. Replacing a file in the database with a BDR server component
>
> 4\. Deletion of the file from the file transfer area on the server disk
> by the BDR server component.

### Use Cases – Administration (A)

#### Define an attribute group

A new Attribute Group is created for the specified entity type and group
name.

#### Delete an attribute group

Allows you to delete an Attribute Group with the specified name and
describing the indicated type of entity.

#### Define an attribute

For a given type of entity and a Group of Attributes indicated by name,
a new attribute belonging to this group is created. Other parameters
specify the data type of the attribute, the type of units (if
applicable), whether it is an attribute with enumerated values, and
whether its value is generated by a given plug-in.

#### Delete an attribute

Deletes an attribute identified by the type of entity being described,
the name of the Attribute Group to which it belongs, and the name of the
attribute.

#### Adding an enumeration value for an Attribute

For an attribute identified by the type of entity being described, the
name of the Attribute Group to which it belongs and the name of the
attribute allows you to add a new enumeration value. Allows you to
specify whether the existing enumeration values of this attribute should
be retained or removed.

#### Clear the download area

Allows you to delete files that were shared as part of a File Download
case, rather than deleted due to lack of confirmation from the client.
Takes as a parameter the number of minutes that represents the "age" of
the oldest file share to be cleared.

### Use Cases - Authorization

#### Checking the presence of the User's account P

It allows the user to check if their login is registered at the BDR
level.

#### Creation of a User account A

Creates a user with the given login name, first name, and last name.

#### Retrieving the list of Users A

Allows you to download a list of registered BDR users.

#### Assigning User permissions to a Session At

It allows you to assign permissions to a specific Session to a user who
is not its owner. The command to grant permissions specifies the login
of the authorized user, the session ID, and a flag that specifies
whether the permissions include writing. The order can be executed by
the session owner or the administrator.

#### Revocation of the User's rights to the Session At

It allows you to take away the rights to a specific Session from a user
who is not its owner. The command to grant permissions specifies the
login of the user to whom the permission is to be revoked, the session
ID, and a flag that specifies whether the permissions include writing.
The order can be executed by the session owner or the administrator.

#### Change the Session visibility setting At

Adjusts the global visibility settings for a given session, as defined
by an ID, to one of the following states:

-   Private

-   public read-only

-   public with a record.

#### Retrieving the list of permissions for a given Session At

For the Session indicated by the identifier, it allows you to download a
list containing logins and types of permissions of users authorized to
it.

#### Checking the update rights of a given Resource At

Triggering an order with an indication of the type of Resource (entity)
and its identifier allows you to determine whether the entity executing
the order has update rights to it.

### Use Cases – Private Area (P)

#### Updating the Filter Set P

Allows you to write active user-defined filters for generic queries. A
tuple structure consisting of a set of filter expressions is passed.

#### Retrieving a list of saved Filters P

Allows you to recreate a set of filter expressions belonging to a given
user from a previous record in BDR. In response to the verb, a tuple
structure consisting of a set of filter expressions is returned.

#### Retrieving the list of User's Shopping Carts P

For the user's request identified by the authentication mechanism, a
list of names defined by the user and stored in the BDR named Shopping
Carts is downloaded.

#### Creation of a user's shopping cart P

For the user request identified by the authentication mechanism, a
Basket with the name given by the order is created and saved in BDR.

#### Deletion of a user's shopping cart P

For the user request identified by the authentication mechanism, it
allows you to delete the Basket with the specified name saved in BDR.

### Placing an Asset in the User's Cart P

A resource of the indicated type and identifier is associated with the
Basket specified by the name. A shopping cart with this name must be
previously defined for a given user in BDR.

#### Removal of an Asset from the User's Cart P

A resource with the indicated type and identifier is removed from the
Cart specified by the name. A shopping cart with such a name must be
previously defined for a given user in the BDR and contain the indicated
resource.

### Retrieving the list of Resources in the User's Shopping Cart (abstract) P

The order, indicating the name of the basket defined earlier for the
current user, allows you to retrieve a list of resources of a given
type. The specifications of this case allow you to list the following
items in the shopping cart:

-   Performers

-   Session

-   Measurement tests

#### Saving an individual Display Configuration P

Allows you to save your preferences for the client software to display
the appropriate Attribute Groups and the individual attributes in these
groups. A structure containing a list of Attributes and a list of
Attribute Groups is passed in the order.

#### Downloading a Saved Display Configuration P

It allows you to read the user's preferences regarding the display by
the client software of the appropriate Attribute Groups and individual
attributes in these groups. As a result of the order, a structure
containing a list of Attributes and a list of Attribute Groups is
obtained.
