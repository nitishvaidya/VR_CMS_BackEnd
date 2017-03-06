$(document).ready(
		function() {

			var url = "http://192.168.1.100:5050/VR_CMS";

			$('#contact_form').bootstrapValidator({
				// To use feedback icons, ensure that
				// you use Bootstrap v3.1.0 or later
				feedbackIcons : {
					valid : 'glyphicon glyphicon-ok',
					invalid : 'glyphicon glyphicon-remove',
					validating : 'glyphicon glyphicon-refresh'
				},
				fields : {
					name : {
						validators : {
							stringLength : {
								min : 2,
							},
							notEmpty : {
								message : 'Please enter name'
							}
						}
					},
					type : {
						validators : {
							notEmpty : {
								message : 'Please the type'
							}
						}
					},
					category : {
						validators : {
							notEmpty : {
								message : 'Please enter the category'
							},
						}
					},
					imageName : {
						validators : {
							stringLength : {
								min : 2,
							},
							notEmpty : {
								message : 'Please enter image name'
							}
						}
					},
					imageDir : {
						validators : {
							notEmpty : {
								message : 'Please enter image directory'
							}
						}
					},
					videoName : {
						validators : {
							stringLength : {
								min : 2,
							},
							notEmpty : {
								message : 'Please enter video name'
							}
						}
					},
					videoDir : {
						validators : {
							notEmpty : {
								message : 'Please enter video directory'
							}
						}
					},
					serverList : {
						validators : {
							notEmpty : {
								message : 'Please enter school servers'
							}
						}
					},
				}
			});

			$('#video_upload_btn').click(
					function() {
						$.post($('#contact_form').attr('action',
								url + "/addOrUpdateVideo"), $('#contact_form')
								.serialize(), function(result) {
							console.log(result);
						}, 'json');
						console.log($('#contact_form').serialize());
					});

			$('#register_form').bootstrapValidator({
				// To use feedback icons, ensure that
				// you use Bootstrap v3.1.0 or later
				feedbackIcons : {
					valid : 'glyphicon glyphicon-ok',
					invalid : 'glyphicon glyphicon-remove',
					validating : 'glyphicon glyphicon-refresh'
				},
				fields : {
					name : {
						validators : {
							stringLength : {
								min : 2,
							},
							notEmpty : {
								message : 'Please enter name'
							}
						}
					},
					phone : {
						validators : {
							notEmpty : {
								message : 'Please supply your phone number'
							}
						}
					},
					address : {
						validators : {
							stringLength : {
								min : 8,
							},
							notEmpty : {
								message : 'Please supply your street address'
							}
						}
					},
					city : {
						validators : {
							stringLength : {
								min : 4,
							},
							notEmpty : {
								message : 'Please supply your city'
							}
						}
					},
				}
			});

			$('#register_school_btn').click(
					function() {
						$.post($('#register_form').attr('action',
								url + "/addOrUpdateSchool"),
								$('#register_form').serialize(), function(
										result) {
									console.log(result);
								}, 'json');
						console.log($('#register_form').serialize());
					});

		});