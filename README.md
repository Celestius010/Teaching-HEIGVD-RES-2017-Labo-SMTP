# Prank mail generator

## Introduction

This project is a prank email generator. It can send email to a list of targets from an target's email.
Victim's email and mail content are entirely configurable through text file.

When the prank generator is executed, each victim is randomly assigned to a group. Then for each group a victim is randomly choosent to be the sender of the prank, all other group members will receive the prank email.

## Usage

## Configuration and usage
A properties configuration file is used to degine the number of group to create and the adress and port of the SMTP server.
The file must be in the `config` folder at the root of the project.

Here is an example of the file:
```
smtpServerPort=2525
smtpServerAddress=localhost
numberOfGroups=3
```

Group must have at least 3 members (one sender and two victims).

The file which contains the victim's email must be named `config/victims` and containe one address per line.

The file with the mail content must be in the same directory and named `pranks`. A message must start with the mail subject and end with a 
line which contains these three characters `~~~`.

Here is an example of a `pranks` file:
```
Subject: My awesome first pranked email
Hello,

You've benn pranked !
~~~
Subject: Another prank
Hello,

This is the second prank email template.
~~~
```

## Implementation
We organized the project mainly as describe in the 4th webcast.

The SMTP package contains the SMTPClient class which is responsible of sending Mail to the SMTP server.

The configuration package has only one class, **Configuration**, which read and parses the three configuration file.
It provides email and victims list to other class.

The model package contains five different class:

* The **PrankGenerator** created a prank campaign. Generate groups and prank depending on the number of groups defined in the configuration.

* **Mail** has all the email information such as senders, receivers, content, and so on. The SMTPClient need a Mail object
to send a mail.

* **Person** and **Group** are simple class. A person is only defined by a mail address and a Group simply a list of Person
