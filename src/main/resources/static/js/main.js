Handlebars.registerHelper('hasMilestone', function (issueMilestoneId, milestoneId) {
    return issueMilestoneId === milestoneId;
})