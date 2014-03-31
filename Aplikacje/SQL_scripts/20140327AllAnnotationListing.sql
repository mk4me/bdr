use Motion;
go

-- created 2014-03-27
create procedure list_complete_annotations_xml (@user_login varchar(30))
as
with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
select
	IdProba as TrialID,
	Status as Status,
	Komentarz as Comment,
	Uwagi as Note
	from dbo.user_reviewable_annotations( dbo.identify_user (@user_login)) Annotation
	where Annotation.status = 4
    for XML AUTO, ELEMENTS, root ('CompletedAnnotations')
go


-- created 2014-03-27
create procedure list_all_annotations_xml (@user_login varchar(30))
as
with XMLNAMESPACES (DEFAULT 'http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService')
select
	IdProba as TrialID,
	Status as Status,
	Komentarz as Comment,
	Uwagi as Note
	from dbo.user_reviewable_annotations( dbo.identify_user (@user_login)) Annotation
    for XML AUTO, ELEMENTS, root ('Annotations')
go

exec list_all_annotations_xml 'habela'