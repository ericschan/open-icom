package icom.jpql.bdk.filters;

import icom.QLException;
import icom.info.AddressableInfo;
import icom.info.PersonalInfo;
import icom.info.beehive.BeehiveOrganizationUserInfo;
import icom.jpa.bdk.dao.OrganizationUserDAO;
import icom.jpql.stack.SimpleExpression;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import com.oracle.beehive.AddressPredicate;
import com.oracle.beehive.BeeId;
import com.oracle.beehive.EntityAddress;
import com.oracle.beehive.FamilyNamePredicate;
import com.oracle.beehive.GivenNamePredicate;
import com.oracle.beehive.ManagerPredicate;
import com.oracle.beehive.OrganizationUser;
import com.oracle.beehive.Predicate;

public class OrganizationUserPredicateFactory extends BaseAccessorPredicateFactory {
    
    static OrganizationUserPredicateFactory singleton = new OrganizationUserPredicateFactory();
    
    public static OrganizationUserPredicateFactory getInstance() {
        return singleton;
    }
    
    public OrganizationUserPredicateFactory() {
    }

    public String getResourceId(BeeId id) {
        return null;
    }

    public String getResourceType() {
        return OrganizationUserDAO.getInstance().getResourceType();
    }

    public String resolveDataAccessStateObjectClassName() {
        return OrganizationUser.class.getSimpleName();
    }
    
    {
        attributeTypeMapper.put(AddressableInfo.Attributes.primaryAddress.name().toLowerCase(), EntityAddress.class);
        attributeTypeMapper.put(AddressableInfo.Attributes.entityAddresses.name().toLowerCase(), EntityAddress.class);
        attributeTypeMapper.put(PersonalInfo.Attributes.familyName.name().toLowerCase(), String.class);
        attributeTypeMapper.put(PersonalInfo.Attributes.givenName.name().toLowerCase(), String.class);
        attributeTypeMapper.put(BeehiveOrganizationUserInfo.Attributes.manager.name().toLowerCase(), OrganizationUser.class);
    }
    
    {
        equalsPredicateMapper.put(BeehiveOrganizationUserInfo.Attributes.manager.name().toLowerCase(), ManagerPredicate.class);
        equalsPredicateMapper.put(PersonalInfo.Attributes.familyName.name().toLowerCase(), FamilyNamePredicate.class);
        equalsPredicateMapper.put(PersonalInfo.Attributes.givenName.name().toLowerCase(), GivenNamePredicate.class);
        equalsPredicateMapper.put(AddressableInfo.Attributes.primaryAddress.name().toLowerCase(), AddressPredicate.class);
    }
    
    {
        likePredicateMapper.put(PersonalInfo.Attributes.familyName.name().toLowerCase(), FamilyNamePredicate.class);
        likePredicateMapper.put(PersonalInfo.Attributes.givenName.name().toLowerCase(), GivenNamePredicate.class);
    }
    
    {
        collectionMemberPredicateMapper.put(AddressableInfo.Attributes.entityAddresses.name().toLowerCase(), AddressPredicate.class);
    }
    
    {predicateConstructorMapper.put(
            
        FamilyNamePredicate.class, new PredicateConstructor() {
            
            public Predicate createPredicate(ArgumentCollection arguments) {
                FamilyNamePredicate predicate = new FamilyNamePredicate();
                Argument firstArgument = arguments.firstArgument;
                boolean patternMatch = false;
                if (arguments.secondArgument != null) {
                    patternMatch = (Boolean) arguments.secondArgument.value;
                }
                if (patternMatch) {
                    predicate.setMatch((String) firstArgument.value);
                    predicate.setPattern((String) firstArgument.value);
                    predicate.setPatternMatch(true);
                } else {
                    predicate.setMatch((String) firstArgument.value);
                    predicate.setPatternMatch(false);
                }
                predicate.setCaseSensitiveMatch(false);
                return predicate;
            }
            
            public Predicate createPredicate(SimpleExpression expression, Map<String, Class<?>> predicateMapper) {
                ArgumentCollection arguments = new ArgumentCollection();
                Argument firstArgument = convertOperandToArgument(expression.primary, expression.firstOperand);
                arguments.firstArgument = firstArgument;
                if (predicateMapper == likePredicateMapper) {
                    arguments.secondArgument = new Argument();
                    arguments.secondArgument.name = "";
                    arguments.secondArgument.value = new Boolean(true);
                }
                return createPredicate(arguments);
            }
        }
    );}
    
    {predicateConstructorMapper.put(
            
        GivenNamePredicate.class, new PredicateConstructor() {
            
            public Predicate createPredicate(ArgumentCollection arguments) {
                GivenNamePredicate predicate = new GivenNamePredicate();
                Argument firstArgument = arguments.firstArgument;
                boolean patternMatch = false;
                if (arguments.secondArgument != null) {
                    patternMatch = (Boolean) arguments.secondArgument.value;
                }
                if (patternMatch) {
                    predicate.setMatch((String) firstArgument.value);
                    predicate.setPattern((String) firstArgument.value);
                    predicate.setPatternMatch(true);
                } else {
                    predicate.setMatch((String) firstArgument.value);
                    predicate.setPatternMatch(false);
                }
                predicate.setCaseSensitiveMatch(false);
                return predicate;
            }
            
            public Predicate createPredicate(SimpleExpression expression, Map<String, Class<?>> predicateMapper) {
                ArgumentCollection arguments = new ArgumentCollection();
                Argument firstArgument = convertOperandToArgument(expression.primary, expression.firstOperand);
                arguments.firstArgument = firstArgument;
                if (predicateMapper == likePredicateMapper) {
                    arguments.secondArgument = new Argument();
                    arguments.secondArgument.name = "";
                    arguments.secondArgument.value = new Boolean(true);
                }
                return createPredicate(arguments);
            }
        }
    );}
    
    {predicateConstructorMapper.put(
            
        ManagerPredicate.class, new PredicateConstructor() {
        
            public Predicate createPredicate(ArgumentCollection arguments) {
                ManagerPredicate predicate = new ManagerPredicate();
                Argument firstArgument = arguments.firstArgument;
                predicate.setManagerMatch((BeeId) firstArgument.value);
                return predicate;
            }
            
            public Predicate createPredicate(SimpleExpression expression, Map<String, Class<?>> predicateMapper) {
                ArgumentCollection arguments = new ArgumentCollection();
                Argument firstArgument = convertOperandToArgument(expression.primary, expression.firstOperand);
                arguments.firstArgument = firstArgument;
                return createPredicate(arguments);
            }   
        }
        
    );}
    
    {predicateConstructorMapper.put(
            
        AddressPredicate.class, new PredicateConstructor() {
        
            public Predicate createPredicate(ArgumentCollection arguments) {
                AddressPredicate predicate = new AddressPredicate();
                Argument firstArgument = arguments.firstArgument;
                EntityAddress address = (EntityAddress) firstArgument.value;
                String addressStr = address.getAddress();
                String schemeSpecificPart = null;
                String scheme = null;
                try {
                    URI uri = new URI(addressStr);
                    scheme = uri.getScheme();
                    schemeSpecificPart = uri.getSchemeSpecificPart();
                } catch (URISyntaxException ex) {
                    throw new QLException("Not Supported", ex);
                }
                predicate.setTypeMatch(address.getAddressType());
                predicate.setValueMatch(schemeSpecificPart);
                predicate.setSchemeMatch(scheme);
                return predicate;
            }
            
            public Predicate createPredicate(SimpleExpression expression, Map<String, Class<?>> predicateMapper) {
                ArgumentCollection arguments = new ArgumentCollection();
                Argument firstArgument = convertOperandToArgument(expression.primary, expression.firstOperand);
                arguments.firstArgument = firstArgument;
                return createPredicate(arguments);
            }   
        }
        
    );}
        
    
}
