use Motion;
go

alter table Adnotacja add constraint DF_Uwagi default '' for Uwagi;
go

-- ANNOTATIONS
-- ===========

-- last mod. 2014-04-03
alter procedure list_authors_annotations_xml (@user_login varchar(30))
as
with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
select
	IdUzytkownik as UserID,
	IdProba as TrialID,
	Status as Status,
	Komentarz as Comment,
	Uwagi as Note
	from Adnotacja Annotation
	where IdUzytkownik = dbo.identify_user(@user_login)
    for XML AUTO, ELEMENTS, root ('UserAnnotations')
go




-- last mod. 2014-04-03
alter procedure list_awaiting_annotations_xml (@user_login varchar(30))
as
with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
select
	IdUzytkownik as UserID,
	IdProba as TrialID,
	Status as Status,
	Komentarz as Comment,
	Uwagi as Note
	from dbo.user_reviewable_annotations( dbo.identify_user (@user_login)) Annotation
	where Annotation.status = 3
    for XML AUTO, ELEMENTS, root ('AwaitingAnnotations')
go


-- last mod. 2014-04-03
alter procedure list_reviewers_annotations_xml (@user_login varchar(30))
as
with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
select
	IdUzytkownik as UserID,
	IdProba as TrialID,
	Status as Status,
	Komentarz as Comment,
	Uwagi as Note
	from Adnotacja Annotation
	where IdOceniajacy = dbo.identify_user(@user_login) and Status = 3
    for XML AUTO, ELEMENTS, root ('ReviewedAnnotations')
go

-- last mod. 2014-04-03
alter procedure list_complete_annotations_xml (@user_login varchar(30))
as
with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
select
	IdUzytkownik as UserID,
	IdProba as TrialID,
	Status as Status,
	Komentarz as Comment,
	Uwagi as Note
	from Adnotacja Annotation
	where Annotation.status = 4
    for XML AUTO, ELEMENTS, root ('CompletedAnnotations')
go


-- last mod. 2014-04-03
alter procedure list_all_annotations_xml (@user_login varchar(30))
as
with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
select
	IdUzytkownik as UserID,
	IdProba as TrialID,
	Status as Status,
	Komentarz as Comment,
	Uwagi as Note
	from Adnotacja Annotation
    for XML AUTO, ELEMENTS, root ('Annotations')
go